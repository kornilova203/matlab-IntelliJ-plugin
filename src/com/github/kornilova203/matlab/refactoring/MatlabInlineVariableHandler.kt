package com.github.kornilova203.matlab.refactoring

import com.github.kornilova203.matlab.MatlabLanguage
import com.github.kornilova203.matlab.psi.*
import com.github.kornilova203.matlab.psi.MatlabTypes.*
import com.intellij.lang.Language
import com.intellij.lang.refactoring.InlineActionHandler
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.util.elementType
import com.intellij.psi.util.parentOfTypes
import com.intellij.refactoring.suggested.startOffset
import com.intellij.refactoring.util.CommonRefactoringUtil

class MatlabInlineVariableHandler : InlineActionHandler() {

    override fun isEnabledForLanguage(l: Language?): Boolean = l is MatlabLanguage

    override fun canInlineElement(element: PsiElement?): Boolean {
        return element is MatlabAssignExpr
    }

    override fun inlineElement(project: Project?, editor: Editor?, element: PsiElement?) {
        if (project == null || editor == null || element == null || element !is MatlabAssignExpr) {
            return
        }
        
        val file = element.containingFile
        var selectedElement = file.findElementAt(editor.caretModel.offset)
        if (selectedElement == null || selectedElement is PsiWhiteSpace || selectedElement.elementType == NEWLINE) {
            selectedElement = file.findElementAt(editor.caretModel.offset - 1) ?: return
        }
        selectedElement = selectedElement.parentOfTypes(MatlabRefExpr::class) ?: return

        var isDeclaration = true
        var declaration = selectedElement.findSameDeclaration()
        if (declaration == null) {
            declaration = element
            isDeclaration = false
        }
        val value = declaration.right
        if (value == null) {
            showHint(project, editor, "Variable has no initializer")
            return
        }

        if (declaration.parentOfTypes(MatlabForLoopRange::class, MatlabWhileLoopCondition::class) != null) {
            showHint(project, editor, "Cannot find a single definition to inline")
            return
        }

        val refs = ReferencesSearch.search(declaration).findAll().sortedBy { ref: PsiReference -> ref.element.startOffset }

        val occurrences = refs
                .map { ref -> ref.element }
                .filter { ref ->  ref != declaration.firstChild }
                .takeWhile { ref -> ref.findSameDeclaration() == null }
                .toMutableList()         
        
        if (!occurrences.contains(selectedElement) && !isDeclaration) {
            showHint(project, editor, "Cannot find a single definition to inline")
            return
        }

        if (occurrences.isEmpty()) {
            showHint(project, editor, "Variable '${selectedElement.text}' is never used")
            return
        }

        var removeDeclaration = true
        if (!isDeclaration && !ApplicationManager.getApplication().isUnitTestMode) {
            val localDialog = MatlabInlineLocalDialog(project, selectedElement, occurrences.size)
            if (localDialog.showAndGet()) {
                if (localDialog.isInlineThisOnly) {
                    occurrences.clear()
                    occurrences.add(selectedElement)
                    removeDeclaration = false
                }
            } else {
                return
            }
        }
        
        WriteCommandAction.runWriteCommandAction(project) {
            for (occurrence in occurrences) {
                if (isNeedParenthesis(value, occurrence)) {
                    occurrence.replace(createElementFromText(project, "(${value.text})"))
                } else {
                    occurrence.replace(value)
                }
            }
            if (removeDeclaration) {
                deleteExpr(declaration)
            }
        }
    }

    private fun isNeedParenthesis(newExpr: PsiElement, oldElement: PsiElement): Boolean {
        val targetExpr = oldElement.parent
        if (targetExpr !is MatlabBinaryExpr || newExpr !is MatlabBinaryExpr) {
            return false
        }
        val newPriority: Int = getExpressionPriority(newExpr)
        val targetPriority: Int = getExpressionPriority(targetExpr)
        return targetPriority > newPriority
    }

    private fun getExpressionPriority(expr: MatlabBinaryExpr): Int {
        return when (expr.elementType) {
            OR_EXPR -> 1
            AND_EXPR -> 2
            MATRIX_OR_EXPR -> 3
            MATRIX_AND_EXPR -> 4
            EQUAL_EXPR, NOT_EQUAL_EXPR, LESS_EXPR, LESS_OR_EQUAL_EXPR, MORE_EXPR, MORE_OR_EQUAL -> 5
            RANGE_EXPR -> 6
            PLUS_EXPR, MINUS_EXPR -> 7
            MUL_EXPR, ELEMENT_WISE_MUL_EXPR, RDIV_EXPR, ELEMENT_WISE_RDIV_EXPR, LDIV_EXPR, ELEMENT_WISE_LDIV_EXPR, POW_EXPR, ELEMENT_WISE_POW_EXPR -> 8
            UNARY_DEC_EXPR, UNARY_INC_EXPR, UNARY_MIN_EXPR, UNARY_NEGATION_EXPR, UNARY_PLUS_EXPR, UNARY_PREFIX_DEC_EXPR, UNARY_PREFIX_INC_EXPR -> 9
            else -> 0
        }
    }

    private fun showHint(project: Project, editor: Editor, message: String) {
        CommonRefactoringUtil.showErrorHint(project, editor, "Cannot perform refactoring\n$message", "Inline Variable", "refactoring.inlineVariable")
    }
}

fun PsiElement.findSameDeclaration(): MatlabAssignExpr? {
    val assign = this.parentOfTypes(MatlabAssignExpr::class) ?: return null
    return if (this == assign.left) assign else null
}
