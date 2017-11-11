package com.github.kornilova_l.matlab

import com.github.kornilova_l.matlab.psi.MatlabTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.psi.TokenType


class MatlabSyntaxHighlighter : SyntaxHighlighterBase() {
    companion object {
        val SEPARATOR = createTextAttributesKey("SIMPLE_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val KEY = createTextAttributesKey("SIMPLE_KEY", DefaultLanguageHighlighterColors.KEYWORD)
        val VALUE = createTextAttributesKey("SIMPLE_VALUE", DefaultLanguageHighlighterColors.STRING)
        val COMMENT = createTextAttributesKey("SIMPLE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER = createTextAttributesKey("SIMPLE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

        private val BAD_CHAR_KEYS: Array<TextAttributesKey?> = arrayOf(BAD_CHARACTER)
        private val SEPARATOR_KEYS: Array<TextAttributesKey?> = arrayOf(SEPARATOR)
        private val KEY_KEYS: Array<TextAttributesKey?> = arrayOf(KEY)
        private val VALUE_KEYS: Array<TextAttributesKey?> = arrayOf(VALUE)
        private val COMMENT_KEYS: Array<TextAttributesKey?> = arrayOf(COMMENT)
        private val EMPTY_KEYS = arrayOfNulls<TextAttributesKey>(0)
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey?> {
        if (tokenType == null) {
            return EMPTY_KEYS
        }
        return when (tokenType) {
            MatlabTypes.SEPARATOR -> SEPARATOR_KEYS
            MatlabTypes.KEY -> KEY_KEYS
            MatlabTypes.VALUE -> VALUE_KEYS
            MatlabTypes.COMMENT -> COMMENT_KEYS
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }
    }

    override fun getHighlightingLexer(): Lexer = MatlabLexerAdapter()
}