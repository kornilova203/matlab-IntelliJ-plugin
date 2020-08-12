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
    private val builder = ControlFlowBuilder()

    fun buildControlFlow(owner: PsiElement): ControlFlow? {
        return builder.build(this, owner)
    }

    override fun visitElement(element: PsiElement) {
        element.acceptChildren(this)
    }

    override fun visitExpr(node: MatlabExpr) {
        builder.startNode(node)
        super.visitExpr(node)
    }

    override fun visitWhileLoop(node: MatlabWhileLoop) {
        val instruction = builder.startNode(node)
        val block = node.block
        val condition = node.whileLoopCondition
        condition?.accept(this)
        val statically: Boolean? = evaluateAsBoolean(condition?.expr)
        if (statically != true) {
            builder.addPendingEdge(node, builder.prevInstruction)
        }
        if (statically == false) {
            builder.prevInstruction = null
        }
        builder.startConditionalNode(block, condition, true)
        block?.accept(this)
        if (builder.prevInstruction != null) {
            builder.addEdge(builder.prevInstruction, instruction)
        }
        builder.checkPending(instruction)
        builder.flowAbrupted()
    }

    override fun visitForLoop(node: MatlabForLoop) {
        val instruction = builder.startNode(node)
        val block = node.block
        val loopRange = node.forLoopRange?.assignExpr
        loopRange?.accept(this)
        builder.addPendingEdge(node, builder.prevInstruction)
        builder.startNode(block)
        block?.accept(this)
        if (builder.prevInstruction != null) {
            builder.addEdge(builder.prevInstruction, instruction)
        }
        builder.checkPending(instruction)
        builder.flowAbrupted()
    }

    override fun visitIfBlock(node: MatlabIfBlock) {
        builder.startNode(node)
        var condition = node.condition
        val elseifList = node.elseifBlockList
        val elseBlock = node.elseBlock
        condition?.accept(this)
        var conditionInstruction = builder.prevInstruction
        var statically: Boolean? = evaluateAsBoolean(condition?.expr)
        var isLastBlock = elseBlock == null && elseifList.isEmpty()
        addConditionBlock(node, condition, node.block, statically, isLastBlock)

        for (elseif in elseifList) {
            builder.prevInstruction = if (statically == true) null else conditionInstruction
            builder.startConditionalNode(elseif, condition, false)
            condition = elseif.condition
            condition?.accept(this)
            conditionInstruction = builder.prevInstruction
            statically = evaluateAsBoolean(condition?.expr)
            isLastBlock = elseBlock == null && elseif == elseifList.last()
            addConditionBlock(node, condition, elseif.block, statically, isLastBlock)
        }

        if (elseBlock != null) {
            builder.prevInstruction = if (statically == true) null else conditionInstruction
            builder.startConditionalNode(elseBlock, condition, false)
            builder.startConditionalNode(elseBlock.block, condition, false)
            elseBlock.accept(this)
        }
    }

    private fun addConditionBlock(node: MatlabIfBlock, condition: MatlabCondition?, block: MatlabBlock?, statically: Boolean?, isLastBlock: Boolean) {
        if (isLastBlock && statically != true) {
            builder.addPendingEdge(node, builder.prevInstruction)
        }
        if (statically == false) {
            builder.flowAbrupted()
        }
        builder.startConditionalNode(block, condition, true)
        block?.accept(this)
        builder.addPendingEdge(node, builder.prevInstruction)
    }

    override fun visitSwitchBlock(node: MatlabSwitchBlock) {
        builder.startNode(node)
        val switchExpr = node.switchExpression
        val caseList = node.caseBlockList
        val otherwise = node.otherwiseBlock
        switchExpr?.accept(this)
        var prevCondition: PsiElement? = null
        var prevConditionInstruction: Instruction? = null
        for (case in caseList) {
            if (prevCondition == null) {
                builder.startNode(case)
            } else {
                builder.prevInstruction = prevConditionInstruction
                builder.startConditionalNode(case, prevCondition, false)
            }
            case.caseExpression?.accept(this)
            prevConditionInstruction = builder.prevInstruction
            prevCondition = case.caseExpression
            builder.startConditionalNode(case.block, case.caseExpression, true)
            case.block?.accept(this)
            builder.addPendingEdge(node, builder.prevInstruction)
        }
        if (otherwise != null) {
            if (prevCondition == null) {
                builder.startNode(otherwise)
                builder.startNode(otherwise.block)
            } else {
                builder.prevInstruction = prevConditionInstruction
                builder.startConditionalNode(otherwise, prevCondition, false)
                builder.startConditionalNode(otherwise.block, prevCondition, false)
            }
            otherwise.block?.accept(this)
        }
    }

    override fun visitTryBlock(node: MatlabTryBlock) {
        builder.startNode(node)
        val block = node.block
        val catch = node.catchBlock
        val firstTryInstruction = builder.instructionCount
        block?.accept(this)
        builder.addPendingEdge(node, builder.prevInstruction)
        val lastTryInstruction = builder.instructionCount
        builder.flowAbrupted()
        if (catch != null) {
            val catchInstruction = builder.startNode(catch)
            catch.block?.accept(this)
            var tryCnt = 0
            for (i in firstTryInstruction until lastTryInstruction) {
                val element = builder.instructions[i].element
                if (element is MatlabTryBlock) {
                    tryCnt++
                }
                if (element is MatlabCatchBlock) {
                    tryCnt--
                }
                if (element is MatlabExpr && tryCnt == 0) {
                    builder.addEdge(builder.instructions[i], catchInstruction)
                }
            }
        }
    }

    override fun visitFunctionDeclaration(node: MatlabFunctionDeclaration) {
        val backupPrevInstruction = builder.prevInstruction
        builder.flowAbrupted()
        val pendingBackup = builder.pending
        builder.pending = mutableListOf()
        builder.startNode(node)
        node.block?.accept(this)
        builder.prevInstruction = backupPrevInstruction
        builder.pending = pendingBackup
    }

    override fun visitClassDeclaration(node: MatlabClassDeclaration) {
        builder.startNode(node)
        val methods = node.methodsBlockList
        for (method in methods) {
            method.block?.accept(this)
        }
    }

    override fun visitControlExpr(node: MatlabControlExpr) {
        val instruction = builder.startNode(node)
        val loop = findLoop(node)
        if (loop != null) {
            when (node.firstChild.elementType) {
                MatlabTypes.BREAK -> builder.addPendingEdge(loop, instruction)
                MatlabTypes.CONTINUE -> builder.addEdge(instruction, builder.findInstructionByElement(loop))
            }
        }
        builder.flowAbrupted()
    }

    private fun findLoop(expr: MatlabControlExpr): PsiElement? {
        return expr.parentOfTypes(MatlabWhileLoop::class, MatlabForLoop::class)
    }
}