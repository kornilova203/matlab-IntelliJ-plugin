package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.psi.*
import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.elementType

open class MatlabUnnecessaryControlExprVisitor(private val holder: ProblemsHolder,
                                               val type: IElementType,
                                               val exprName: String) : MatlabVisitor() {
    fun findLastReturn(scope: PsiElement?) {
        if (scope == null) return
        var lastChild = scope.lastChild
        while (lastChild != null && lastChild.elementType in setOf(TokenType.WHITE_SPACE, MatlabTypes.NEWLINE, MatlabTypes.SEMICOLON, MatlabTypes.FUNCTION_DECLARATION)) {
            lastChild = lastChild.prevSibling
        }
        if (lastChild is MatlabControlExpr && lastChild.firstChild.elementType == type) {
            registerProblem(lastChild)
            return
        }
        when (lastChild) {
            is MatlabIfBlock -> {
                val ifBlock = lastChild
                findLastReturn(ifBlock.block)
                findLastReturn(ifBlock.elseBlock?.block)
                for (elseif in ifBlock.elseifBlockList) {
                    findLastReturn(elseif.block)
                }
            }
            is MatlabSwitchBlock -> {
                for (case in lastChild.caseBlockList) {
                    findLastReturn(case.block)
                }
            }
            is MatlabTryBlock -> {
                findLastReturn(lastChild.block)
                findLastReturn(lastChild.catchBlock?.block)
            }
        }
    }

    private fun registerProblem(node: PsiElement?) {
        if (node == null) return
        holder.registerProblem(node,
                "'${exprName}' statement is unnecessary",
                object : LocalQuickFixOnPsiElement(node) {
                    override fun getFamilyName() = name
                    override fun getText() = "Remove unnecessary '${exprName}' statement"
                    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
                        if (node.nextSibling is PsiWhiteSpace) node.nextSibling.delete()
                        if (node.prevSibling is PsiWhiteSpace) node.prevSibling.delete()
                        if (node.nextSibling.elementType == MatlabTypes.SEMICOLON) node.nextSibling.delete()
                        if (node.nextSibling is PsiWhiteSpace) node.nextSibling.delete()
                        when {
                            node.nextSibling.elementType == MatlabTypes.NEWLINE -> node.nextSibling
                            node.prevSibling.elementType == MatlabTypes.NEWLINE -> node.prevSibling
                            else -> null
                        }?.delete()
                        node.delete()
                    }
                })
    }
}