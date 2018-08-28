package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.MatlabLanguage
import com.intellij.psi.tree.IElementType

class MatlabTokenType(debugName: String) : IElementType(debugName, MatlabLanguage.INSTANCE) {
    override fun toString(): String {
        return "MatlabTokenType." + super.toString()
    }
}