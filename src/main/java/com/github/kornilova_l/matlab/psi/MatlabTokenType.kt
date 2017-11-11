package com.github.kornilova_l.matlab.psi

import com.github.kornilova_l.matlab.MatlabLanguage
import com.intellij.psi.tree.IElementType

class MatlabTokenType(debugName: String) : IElementType(debugName, MatlabLanguage.INSTANCE) {
    override fun toString(): String {
        return "MatlabTokenType." + super.toString()
    }
}