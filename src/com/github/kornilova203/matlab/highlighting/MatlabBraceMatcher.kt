package com.github.kornilova203.matlab.highlighting

import com.github.kornilova203.matlab.psi.MatlabTypes
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

class MatlabBraceMatcher : PairedBraceMatcher {
    private val pairs = arrayOf(
            BracePair(MatlabTypes.LPARENTH, MatlabTypes.RPARENTH, false),
            BracePair(MatlabTypes.LBRACE, MatlabTypes.RBRACE, true),
            BracePair(MatlabTypes.LBRACKET, MatlabTypes.RBRACKET, false)
    )

    override fun getPairs(): Array<BracePair> = pairs

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return true
    }

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int {
        // TODO: implement after we have grammar
        return 0
    }
}
