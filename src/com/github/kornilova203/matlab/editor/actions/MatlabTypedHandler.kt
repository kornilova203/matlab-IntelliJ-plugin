package com.github.kornilova203.matlab.editor.actions

import com.github.kornilova203.matlab.psi.MatlabCaseBlock
import com.github.kornilova203.matlab.psi.MatlabTypes
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import com.intellij.util.DocumentUtil

class MatlabTypedHandler : TypedHandlerDelegate() {
    override fun beforeCharTyped(c: Char, project: Project, editor: Editor, file: PsiFile, fileType: FileType): Result {
        if (c.isWhitespace()) {
            reduceIndent(project, editor, file)
        }
        return Result.CONTINUE
    }
}

fun reduceIndent(project: Project, editor: Editor, file: PsiFile): Int {
    val offset = editor.caretModel.offset
    val element = file.findElementAt(offset - 1) ?: return -1
    val startBlockOffset = when (element.elementType) {
        MatlabTypes.END -> element.parent.startOffset
        MatlabTypes.ELSE, MatlabTypes.ELSEIF, MatlabTypes.CATCH -> element.parent.parent.startOffset
        MatlabTypes.CASE, MatlabTypes.OTHERWISE -> {
            val prevSibling = PsiTreeUtil.skipWhitespacesAndCommentsBackward(element.parent)
            if (prevSibling is MatlabCaseBlock) prevSibling.startOffset else return -1
        }
        else -> return -1
    }
    val document = editor.document
    val line = document.getLineNumber(offset)
    if (element.startOffset == DocumentUtil.getFirstNonSpaceCharOffset(document, line)) {
        val indent = DocumentUtil.getIndent(document, startBlockOffset)
        document.replaceString(document.getLineStartOffset(line), element.startOffset, indent)
        PsiDocumentManager.getInstance(project).commitDocument(document)
        return element.endOffset
    }
    return -1
}