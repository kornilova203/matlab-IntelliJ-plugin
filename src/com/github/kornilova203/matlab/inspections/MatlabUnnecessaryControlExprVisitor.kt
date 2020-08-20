package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.psi.*
import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.elementType

open class MatlabUnnecessaryControlExprVisitor(private val holder: ProblemsHolder,
                                               val type: IElementType,
                                               val exprName: String) : MatlabVisitor() {
    fun findUnnecessaryExpr(scope: PsiElement?) {
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
                findUnnecessaryExpr(ifBlock.block)
                findUnnecessaryExpr(ifBlock.elseBlock?.block)
                for (elseif in ifBlock.elseifBlockList) {
                    findUnnecessaryExpr(elseif.block)
                }
            }
            is MatlabSwitchBlock -> {
                for (case in lastChild.caseBlockList) {
                    findUnnecessaryExpr(case.block)
                }
            }
            is MatlabTryBlock -> {
                findUnnecessaryExpr(lastChild.block)
                findUnnecessaryExpr(lastChild.catchBlock?.block)
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
                        deleteExpr(node)
                    }
                })
    }
}