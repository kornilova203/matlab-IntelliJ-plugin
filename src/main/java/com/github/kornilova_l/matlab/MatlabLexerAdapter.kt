package com.github.kornilova_l.matlab

import com.intellij.lexer.FlexAdapter

class MatlabLexerAdapter : FlexAdapter(MatlabLexer(null)) {
}