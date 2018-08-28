package com.github.korniloval.matlab.lexer

import com.github.korniloval.matlab.psi.MatlabTypes.DOUBLE_QUOTE_STRING
import com.intellij.lexer.StringLiteralLexer
import com.intellij.psi.StringEscapesTokenTypes
import com.intellij.psi.tree.IElementType

class DoubleQuoteStringLiteralLexer
    : StringLiteralLexer('"', DOUBLE_QUOTE_STRING, false, null, false, false) {

    private val legalEscapedChars = arrayOf('n', 'r', 'b', 't', 'f', '\\', '"')

    override fun getTokenType(): IElementType? {
        if (myStart >= myEnd) return null

        if (myBuffer[myStart] != '\\') {
            return myOriginalLiteralToken
        }

        if (myStart + 1 >= myEnd) {
            return handleSingleSlashEscapeSequence()
        }
        val nextChar = myBuffer[myStart + 1]
        if (legalEscapedChars.contains(nextChar)) {
            return StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN
        }
        return StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN
    }
}
