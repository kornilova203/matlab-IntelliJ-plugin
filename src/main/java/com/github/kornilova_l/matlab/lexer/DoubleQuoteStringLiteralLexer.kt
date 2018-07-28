package com.github.kornilova_l.matlab.lexer

import com.github.kornilova_l.matlab.psi.MatlabTypes.DOUBLEQUOTESTRINGLITERAL
import com.intellij.lexer.StringLiteralLexer
import com.intellij.psi.StringEscapesTokenTypes
import com.intellij.psi.tree.IElementType

class DoubleQuoteStringLiteralLexer
    : StringLiteralLexer('"', DOUBLEQUOTESTRINGLITERAL, false, null, false, false) {

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
