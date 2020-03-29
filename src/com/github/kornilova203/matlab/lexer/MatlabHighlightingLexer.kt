package com.github.kornilova203.matlab.lexer

import com.github.kornilova203.matlab.psi.MatlabTypes
import com.intellij.lexer.LayeredLexer
import com.intellij.psi.tree.IElementType

class MatlabHighlightingLexer : LayeredLexer(MatlabLexer.getAdapter()) {
    init {
        registerSelfStoppingLayer(DoubleQuoteStringLiteralLexer(),
                arrayOf(MatlabTypes.DOUBLE_QUOTE_STRING), IElementType.EMPTY_ARRAY)

        registerSelfStoppingLayer(SingleQuoteStringLexer.getAdapter(),
                arrayOf(MatlabTypes.SINGLE_QUOTE_STRING), IElementType.EMPTY_ARRAY)
    }
}
