package com.github.korniloval.matlab.editor.smartEnter

import com.github.korniloval.matlab.psi.MatlabAssignExpr
import com.github.korniloval.matlab.psi.MatlabFunctionExpr
import com.intellij.codeInsight.editorActions.smartEnter.SmartEnterProcessor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

class StatementCompletionProcessor : SmartEnterProcessor() {

  override fun process(project: Project, editor: Editor, psiFile: PsiFile): Boolean {
    val statementAtCaret = getStatementAtCaret(editor, psiFile) ?: return false

    val expressionToComplete = findExpressionToComplete(statementAtCaret) { node ->
      when (node) {
        is MatlabFunctionExpr,
        is MatlabAssignExpr -> true
        else -> false
      }
    } ?: return false

    val expressionRange = getExpressionRange(editor, expressionToComplete)
    val fullExpression = editor.document.getText(expressionRange)
    val endOfExpression = expressionToComplete.textRange.endOffset

    return if (shouldCompleteExpression(fullExpression)) {
      editor.document.insertString(endOfExpression, ";")
      editor.caretModel.moveToOffset(endOfExpression + 1)
      true
    } else {
      false
    }
  }

  private fun getExpressionRange(editor: Editor, statementElement: PsiElement): TextRange =
      if (editor.document.textLength <= statementElement.textRange.endOffset) {
        statementElement.textRange
      } else {
        statementElement.textRange.grown(1)
      }

  private fun shouldCompleteExpression(fullExpression: String): Boolean =
      !StringUtil.endsWithChar(fullExpression, ';')

  private fun findExpressionToComplete(node: PsiElement?, expressionDetector: (PsiElement) -> Boolean): PsiElement? {
    val parent = node?.parent
    return when {
      parent == null -> null
      expressionDetector(parent) -> parent
      else -> findExpressionToComplete(parent, expressionDetector)
    }
  }
}