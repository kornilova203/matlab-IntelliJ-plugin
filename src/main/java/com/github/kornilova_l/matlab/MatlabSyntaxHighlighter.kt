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
        private val COMMENT = createTextAttributesKey("SIMPLE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        private val BAD_CHARACTER = createTextAttributesKey("SIMPLE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
        private val STRING = createTextAttributesKey("STRING", DefaultLanguageHighlighterColors.STRING)
        private val SEMICOLON = createTextAttributesKey("SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON)
        private val ID = createTextAttributesKey("ID", DefaultLanguageHighlighterColors.LOCAL_VARIABLE)
        private val KEYWORD = createTextAttributesKey("IF", DefaultLanguageHighlighterColors.KEYWORD)

        private val BAD_CHAR_KEYS: Array<TextAttributesKey?> = arrayOf(BAD_CHARACTER)
        private val COMMENT_KEYS: Array<TextAttributesKey?> = arrayOf(COMMENT)
        private val STRING_KEYS: Array<TextAttributesKey?> = arrayOf(STRING)
        private val SEMICOLON_KEYS: Array<TextAttributesKey?> = arrayOf(SEMICOLON)
        private val KEYWORD_KEYS: Array<TextAttributesKey?> = arrayOf(KEYWORD)
        private val ID_KEYS: Array<TextAttributesKey?> = arrayOf(ID)
        private val CLASSNAME_KEYS: Array<TextAttributesKey?> = arrayOf(DefaultLanguageHighlighterColors.CLASS_NAME)
        private val NUMBER_KEYS: Array<TextAttributesKey?> = arrayOf(DefaultLanguageHighlighterColors.NUMBER)
        private val EMPTY_KEYS = arrayOfNulls<TextAttributesKey>(0)
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey?> {
        if (tokenType == null) {
            return EMPTY_KEYS
        }
        return when (tokenType) {
            MatlabTypes.COMMENT -> COMMENT_KEYS
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            MatlabTypes.STRING -> STRING_KEYS
            MatlabTypes.SEMICOLON -> SEMICOLON_KEYS
            MatlabTypes.ID -> ID_KEYS

            MatlabTypes.FUNCTION -> KEYWORD_KEYS
            MatlabTypes.END -> KEYWORD_KEYS
            MatlabTypes.IF -> KEYWORD_KEYS
            MatlabTypes.ELSE -> KEYWORD_KEYS
            MatlabTypes.ELSEIF -> KEYWORD_KEYS
            MatlabTypes.WHILE -> KEYWORD_KEYS
            MatlabTypes.FOR -> KEYWORD_KEYS
            MatlabTypes.CLASSDEF -> KEYWORD_KEYS
            MatlabTypes.PROPERTIES -> KEYWORD_KEYS
            MatlabTypes.METHODS -> KEYWORD_KEYS

            MatlabTypes.INTEGER -> NUMBER_KEYS
            MatlabTypes.FLOAT -> NUMBER_KEYS
            MatlabTypes.FLOATEXPONENTIAL -> NUMBER_KEYS
            else -> EMPTY_KEYS
        }
    }

    override fun getHighlightingLexer(): Lexer = MatlabLexerAdapter()
}