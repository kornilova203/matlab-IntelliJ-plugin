package com.github.kornilova_l.matlab.lexer

import com.github.kornilova_l.matlab.psi.MatlabTypes
import com.intellij.lexer.LayeredLexer
import com.intellij.psi.tree.IElementType

class MatlabHighlightingLexer : LayeredLexer(MatlabLexer.getAdapter()) {
    init {
        registerSelfStoppingLayer(DoubleQuoteStringLiteralLexer(),
                arrayOf(MatlabTypes.DOUBLEQUOTESTRINGLITERAL), IElementType.EMPTY_ARRAY)

        registerSelfStoppingLayer(SingleQuoteStringLiteralLexer.getAdapter(),
                arrayOf(MatlabTypes.SINGLEQUOTESTRINGLITERAL), IElementType.EMPTY_ARRAY)
    }
}
