package com.github.kornilova203.matlab.editor.actions

import com.github.kornilova203.matlab.psi.MatlabArguments
import com.github.kornilova203.matlab.psi.MatlabTypes
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegateAdapter
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.elementType

class MatlabEnterInStringLiteralHandler : EnterHandlerDelegateAdapter() {
    override fun preprocessEnter(file: PsiFile, editor: Editor, caretOffset: Ref<Int>, caretAdvance: Ref<Int>, dataContext: DataContext, originalHandler: EditorActionHandler?): EnterHandlerDelegate.Result {
        var offset  = caretOffset.get().toInt()
        val element = file.findElementAt(offset)
        if (element == null || element.elementType != MatlabTypes.DOUBLE_QUOTE_STRING && element.elementType != MatlabTypes.SINGLE_QUOTE_STRING) {
            return EnterHandlerDelegate.Result.Continue
        }
        val document = editor.document
        val marker = document.createRangeMarker(element.textRange)
        val quote = if (element.elementType == MatlabTypes.DOUBLE_QUOTE_STRING) "\"" else "\'"
        document.insertString(offset, "$quote,$quote")
        offset += 2
        if (!isInsideStrcat(element)) {
            document.insertString(offset, "        ")
            document.insertString(marker.startOffset, "strcat(")
            document.insertString(marker.endOffset, ")")
            offset += 7
        }
        PsiDocumentManager.getInstance(file.project).commitDocument(editor.document)
        caretOffset.set(offset)
        return EnterHandlerDelegate.Result.Continue
    }

    private fun isInsideStrcat(element: PsiElement) : Boolean {
        val parent = element.parent.parent
        return parent is MatlabArguments && parent.prevSibling.text == "strcat"
    }
}