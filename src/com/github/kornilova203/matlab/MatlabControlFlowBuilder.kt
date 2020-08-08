package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.*
import com.intellij.codeInsight.controlflow.ControlFlow
import com.intellij.codeInsight.controlflow.ControlFlowBuilder
import com.intellij.codeInsight.controlflow.Instruction
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveVisitor
import com.intellij.psi.util.elementType
import com.intellij.psi.util.parentOfTypes

class MatlabControlFlowBuilder : MatlabVisitor(), PsiRecursiveVisitor {
    private val myBuilder = ControlFlowBuilder()

    fun buildControlFlow(owner: PsiElement): ControlFlow? {
        return myBuilder.build(this, owner)
    }

    override fun visitElement(element: PsiElement) {
        element.acceptChildren(this)
    }

    override fun visitExpr(node: MatlabExpr) {
        myBuilder.startNode(node)
        super.visitExpr(node)
    }

    override fun visitWhileLoop(node: MatlabWhileLoop) {
        val instruction = myBuilder.startNode(node)
        val block = node.block
        val condition = node.whileLoopCondition
        condition?.accept(this)
        val statically: Boolean? = evaluateAsBoolean(condition?.expr)
        if (statically != true) {
            myBuilder.addPendingEdge(node, myBuilder.prevInstruction)
        }
        if (statically == false) {
            myBuilder.prevInstruction = null
        }
        myBuilder.startConditionalNode(block, condition, true)
        block?.accept(this)
        if (myBuilder.prevInstruction != null) {
            myBuilder.addEdge(myBuilder.prevInstruction, instruction)
        }
        myBuilder.checkPending(instruction)
        myBuilder.flowAbrupted()
    }

    override fun visitForLoop(node: MatlabForLoop) {
        val instruction = myBuilder.startNode(node)
        val block = node.block
        val loopRange = node.forLoopRange?.assignExpr
        loopRange?.accept(this)
        myBuilder.addPendingEdge(node, myBuilder.prevInstruction)
        myBuilder.startNode(block)
        block?.accept(this)
        if (myBuilder.prevInstruction != null) {
            myBuilder.addEdge(myBuilder.prevInstruction, instruction)
        }
        myBuilder.checkPending(instruction)
        myBuilder.flowAbrupted()
    }

    override fun visitIfBlock(node: MatlabIfBlock) {        //не забудь вынести общую часть и заменить на flowAbrupted и myBuilder-> builder
        myBuilder.startNode(node)
        var condition = node.condition
        val block = node.block
        val elseifList = node.elseifBlockList
        val elseBlock = node.elseBlock
        condition?.accept(this)
        var conditionInstruction = myBuilder.prevInstruction
        var statically: Boolean? = evaluateAsBoolean(condition?.expr)
        if (elseBlock == null && elseifList.isEmpty() && statically != true) {
            myBuilder.addPendingEdge(node, myBuilder.prevInstruction)
        }
        if (statically == false) {
            myBuilder.prevInstruction = null
        }
        myBuilder.startConditionalNode(block, condition, true)
        block?.accept(this)
        myBuilder.addPendingEdge(node, myBuilder.prevInstruction)

        for (elseif in elseifList) {
            myBuilder.prevInstruction = if (statically == true) null else conditionInstruction
            myBuilder.startConditionalNode(elseif, condition, false)
            condition = elseif.condition
            condition?.accept(this)
            conditionInstruction = myBuilder.prevInstruction
            statically = evaluateAsBoolean(condition?.expr)
            if (elseBlock == null && elseif == elseifList.last() && statically != true) {
                myBuilder.addPendingEdge(node, myBuilder.prevInstruction)
            }
            if (statically == false) {
                myBuilder.prevInstruction = null
            }
            myBuilder.startConditionalNode(elseif.block, condition, true)
            elseif.block?.accept(this)
            myBuilder.addPendingEdge(node, myBuilder.prevInstruction)
        }

        if (elseBlock != null) {
            myBuilder.prevInstruction = if (statically == true) null else conditionInstruction
            myBuilder.startConditionalNode(elseBlock, condition, false)
            myBuilder.startConditionalNode(elseBlock.block, condition, false)
            elseBlock.accept(this)
        }
    }

    override fun visitSwitchBlock(node: MatlabSwitchBlock) {
        myBuilder.startNode(node)
        val switchExpr = node.switchExpression
        val caseList = node.caseBlockList
        val otherwise = node.otherwiseBlock
        switchExpr?.accept(this)
        var prevCondition: PsiElement? = null
        var prevConditionInstruction: Instruction? = null
        for (case in caseList) {
            if (prevCondition == null) {
                myBuilder.startNode(case)
            } else {
                myBuilder.prevInstruction = prevConditionInstruction
                myBuilder.startConditionalNode(case, prevCondition, false)
            }
            case.caseExpression?.accept(this)
            prevConditionInstruction = myBuilder.prevInstruction
            prevCondition = case.caseExpression
            myBuilder.startConditionalNode(case.block, case.caseExpression, true)
            case.block?.accept(this)
            myBuilder.addPendingEdge(node, myBuilder.prevInstruction)
        }
        if (otherwise != null) {
            if (prevCondition == null) {
                myBuilder.startNode(otherwise)
                myBuilder.startNode(otherwise.block)
            } else {
                myBuilder.prevInstruction = prevConditionInstruction
                myBuilder.startConditionalNode(otherwise, prevCondition, false)
                myBuilder.startConditionalNode(otherwise.block, prevCondition, false)
            }
            otherwise.block?.accept(this)
        }
    }

    override fun visitTryBlock(node: MatlabTryBlock) {
        myBuilder.startNode(node)
        val block = node.block
        val catch = node.catchBlock
        val firstTryInstruction = myBuilder.instructionCount
        block?.accept(this)
        myBuilder.addPendingEdge(node, myBuilder.prevInstruction)
        val lastTryInstruction = myBuilder.instructionCount
        myBuilder.flowAbrupted()
        if (catch != null) {
            val catchInstruction = myBuilder.startNode(catch)
            catch.block?.accept(this)
            for (i in firstTryInstruction until lastTryInstruction) {
                myBuilder.addEdge(myBuilder.instructions[i], catchInstruction)
            }
        }
    }

    override fun visitFunctionDeclaration(node: MatlabFunctionDeclaration) {
        val backupPrevInstruction = myBuilder.prevInstruction
        myBuilder.flowAbrupted()
        myBuilder.startNode(node)
        node.block?.accept(this)
        myBuilder.prevInstruction = backupPrevInstruction
    }

    override fun visitClassDeclaration(node: MatlabClassDeclaration) {
        myBuilder.startNode(node)
        val methods = node.methodsBlockList
        for (method in methods) {
            method.block?.accept(this)
        }
    }

    override fun visitControlExpr(node: MatlabControlExpr) {
        val instruction = myBuilder.startNode(node)
        val loop = findLoop(node)
        if (loop != null) {
            when (node.firstChild.elementType) {
                MatlabTypes.BREAK -> myBuilder.addPendingEdge(loop, instruction)
                MatlabTypes.CONTINUE -> myBuilder.addEdge(instruction, myBuilder.findInstructionByElement(loop))
            }
        }
        myBuilder.flowAbrupted()
    }

    private fun findLoop(expr: MatlabControlExpr): PsiElement? {
        return expr.parentOfTypes(MatlabWhileLoop::class, MatlabForLoop::class)
    }
}