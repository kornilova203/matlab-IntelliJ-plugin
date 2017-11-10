package com.github.kornilova_l.matlab

import com.intellij.lexer.Lexer
import com.intellij.lexer.LexerPosition
import com.intellij.psi.tree.IElementType

class MatlabLexer : Lexer() {
    override fun getState(): Int {
        return 0
    }

    override fun getTokenStart(): Int {
    }

    override fun getBufferEnd(): Int {
    }

    override fun getCurrentPosition(): LexerPosition {
    }

    override fun getBufferSequence(): CharSequence {
    }

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
    }

    override fun getTokenType(): IElementType? {
    }

    override fun advance() {
    }

    override fun getTokenEnd(): Int {
    }

    override fun restore(position: LexerPosition) {
    }
}