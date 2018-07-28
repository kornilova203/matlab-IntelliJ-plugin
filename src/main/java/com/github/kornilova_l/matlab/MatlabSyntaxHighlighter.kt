package com.github.kornilova_l.matlab

import com.github.kornilova_l.matlab.lexer.MatlabHighlightingLexer
import com.github.kornilova_l.matlab.psi.MatlabTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.StringEscapesTokenTypes
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType


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
        private val NUMBER_KEYS: Array<TextAttributesKey?> = arrayOf(DefaultLanguageHighlighterColors.NUMBER)
        private val EMPTY_KEYS = arrayOfNulls<TextAttributesKey>(0)
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey?> {
        if (tokenType == null) {
            return EMPTY_KEYS
        }
        return when (tokenType) {
            MatlabTypes.COMMENT -> COMMENT_KEYS

            MatlabTypes.SINGLEQUOTESTRINGLITERAL -> STRING_KEYS
            MatlabTypes.DOUBLEQUOTESTRINGLITERAL -> STRING_KEYS
            StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN -> arrayOf(VALID_STRING_ESCAPE)
            StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN -> arrayOf(INVALID_STRING_ESCAPE)

            MatlabTypes.SEMICOLON -> SEMICOLON_KEYS
            MatlabTypes.IDENTIFIER -> ID_KEYS

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

            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }
    }

    override fun getHighlightingLexer(): Lexer = MatlabHighlightingLexer()
}
