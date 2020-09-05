package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.MatlabTypes
import com.intellij.application.options.CodeStyle
import com.intellij.formatting.IndentInfo
import com.intellij.lang.Language
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.project.Project
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.lineIndent.LineIndentProvider
import com.intellij.psi.tree.IElementType
import com.intellij.util.text.CharArrayUtil


class MatlabLineIndentProvider : LineIndentProvider {
    companion object {
        private val WHITE_SPACE_OR_COMMENT: Array<IElementType> = arrayOf(
                TokenType.WHITE_SPACE,
                MatlabTypes.COMMENT,
                MatlabTypes.NEWLINE
        )

        private val BLOCK_OPENING: Array<IElementType> = arrayOf(
                MatlabTypes.CLASSDEF,
                MatlabTypes.FOR,
                MatlabTypes.FUNCTION,
                MatlabTypes.IF,
                MatlabTypes.SWITCH,
                MatlabTypes.TRY,
                MatlabTypes.WHILE,
                MatlabElementTypes.PROPERTIES,
                MatlabElementTypes.METHODS,
                MatlabElementTypes.EVENTS,
                MatlabElementTypes.ENUMERATION,
                MatlabTypes.PARFOR,
                MatlabTypes.SPMD
        )

        private val BLOCK_CLOSING: Array<IElementType> = arrayOf(
                MatlabTypes.END
        )

        private val CASE_OTHERWISE: Array<IElementType> = arrayOf(
                MatlabTypes.CASE,
                MatlabTypes.OTHERWISE
        )

        private val NOT_END_KEYWORDS: Array<IElementType> = arrayOf(
                MatlabTypes.CATCH,
                MatlabTypes.ELSE,
                MatlabTypes.ELSEIF
        )

        private val INDENT_KEYWORDS: Array<IElementType> = arrayOf(
                *BLOCK_OPENING,
                *CASE_OTHERWISE,
                *NOT_END_KEYWORDS
        )

    }

    override fun getLineIndent(project: Project, editor: Editor, language: Language?, offset: Int): String? {
        if (offset > 0) {
            return getIndent(editor, offset - 1)
        }
        return ""
    }

    private fun getIndent(editor: Editor, offset: Int): String? {
        val currentPosition = MatlabSemanticEditorPosition(editor as EditorEx, offset)
        val before = currentPosition.beforeOptionalMix(*WHITE_SPACE_OR_COMMENT)
        val after = currentPosition.afterOptionalMix(*WHITE_SPACE_OR_COMMENT)
        val startBlock = currentPosition.findLeftParenthesisBackwardsSkippingNested(hashSetOf(*BLOCK_OPENING), hashSetOf(*BLOCK_CLOSING))
        when {
            (after.isAtAnyOf(*BLOCK_CLOSING) || after.isAtAnyOf(*NOT_END_KEYWORDS)) && !currentPosition.after().isAt(MatlabTypes.NEWLINE) -> {
                return getIndentString(editor, startBlock.startOffset(), false)
            }
            after.isAtAnyOf(*CASE_OTHERWISE) && !currentPosition.after().isAt(MatlabTypes.NEWLINE) -> {
                return getIndentString(editor, startBlock.startOffset(), true)
            }
            !startBlock.isAtEnd() && (before.isAtAnyOf(*INDENT_KEYWORDS) || before.isAfterOnSameLine(*INDENT_KEYWORDS)) -> {
                return getIndentString(editor, before.startOffset(), true)
            }
        }
        return null
    }

    private fun getIndentString(editor: Editor, offset: Int, shouldExpand: Boolean): String {
        val settings: CodeStyleSettings = CodeStyle.getSettings(editor)
        val indentOptions = settings.getIndentOptions(MatlabFileType)
        val docChars = editor.document.charsSequence

        var baseIndent = ""
        if (offset > 0) {
            val indentStart = CharArrayUtil.shiftBackwardUntil(docChars, offset, "\n") + 1
            if (indentStart >= 0) {
                val indentEnd = CharArrayUtil.shiftForward(docChars, indentStart, " \t")
                val diff = indentEnd - indentStart
                if (diff > 0) {
                    baseIndent = docChars.subSequence(indentStart, indentEnd).toString()
                }
            }
        }
        if (shouldExpand) {
            baseIndent += IndentInfo(0, indentOptions.INDENT_SIZE, 0).generateNewWhiteSpace(indentOptions)
        }
        return baseIndent
    }

    override fun isSuitableFor(language: Language?): Boolean = language is MatlabLanguage
}


