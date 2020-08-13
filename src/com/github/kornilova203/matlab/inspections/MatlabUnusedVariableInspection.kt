package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.psi.*
import com.intellij.codeInsight.PsiEquivalenceUtil
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.util.elementType

class MatlabUnusedVariableInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean) = MatlabUnusedVariableInspectionVisitor(holder)
}

class MatlabUnusedVariableInspectionVisitor(private val holder: ProblemsHolder) : MatlabVisitor() {
    override fun visitAssignExpr(expr: MatlabAssignExpr) {
        val refs = getReferences(expr)
        if (!refs.isEmpty()) return
        val element = expr.left
        holder.registerProblem(expr.firstChild,
                "Variable '${element.text}' is never used",
                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                object : LocalQuickFixOnPsiElement(element) {
                    override fun getFamilyName(): String = "Remove variable"
                    override fun getText(): String = "Remove variable '${element.text}'"
                    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
                        trim(expr)
                        if (expr.nextSibling.elementType == MatlabTypes.SEMICOLON) {
                            expr.nextSibling.delete()
                            trim(expr)
                        }
                        when {
                            expr.nextSibling.elementType == MatlabTypes.NEWLINE -> expr.nextSibling
                            expr.prevSibling.elementType == MatlabTypes.NEWLINE -> expr.prevSibling
                            else -> null
                        }?.delete()
                        expr.delete()
                    }
                })
    }

    override fun visitParameter(parameter: MatlabParameter) {
        val refs = getReferences(parameter)
        if (!refs.isEmpty()) return
        holder.registerProblem(parameter,
                "Parameter '${parameter.text}' is never used",
                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                object : LocalQuickFixOnPsiElement(parameter) {
                    override fun getFamilyName(): String = "Remove parameter"
                    override fun getText(): String = "Remove parameter '${parameter.text}'"
                    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
                        deleteWhiteSpace(parameter.nextSibling)
                        if (parameter.nextSibling.elementType == MatlabTypes.COMMA) {
                            parameter.nextSibling.delete()
                            deleteWhiteSpace(parameter.nextSibling)
                        } else {
                            deleteWhiteSpace(parameter.prevSibling)
                            if (parameter.prevSibling.elementType == MatlabTypes.COMMA) {
                                parameter.prevSibling.delete()
                            }
                        }
                        parameter.delete()
                    }
                })
    }

    override fun visitCatchBlock(block: MatlabCatchBlock) {
        val exception = block.getChildOfType(MatlabTypes.IDENTIFIER) ?: return
        val refs = getReferences(block)
        if (!refs.isEmpty()) return
        holder.registerProblem(exception,
                "Exception '${exception.text}' is never used",
                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                object : LocalQuickFixOnPsiElement(exception) {
                    override fun getFamilyName(): String = "Remove exception"
                    override fun getText(): String = "Remove exception '${exception.text}'"
                    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
                        trim(exception)
                        exception.delete()
                    }
                }
        )
    }

    fun getReferences(element: PsiElement): MutableCollection<PsiReference> {
        val scope = GlobalSearchScope.fileScope(element.containingFile)
        val query = ReferencesSearch.search(element, scope)
        val refs = query.findAll()
        if (element is MatlabAssignExpr) {
            for (ref in refs) {
                if (PsiEquivalenceUtil.areElementsEquivalent(ref.element, element.left)) {
                    refs.remove(ref)
                    break
                }
            }
        }
        return refs
    }

    private fun trim(element: PsiElement) {
        deleteWhiteSpace(element.nextSibling)
        deleteWhiteSpace(element.prevSibling)
    }

    private fun deleteWhiteSpace(element: PsiElement?) {
        if (element != null && element is PsiWhiteSpace) {
            element.delete()
        }
    }
}

