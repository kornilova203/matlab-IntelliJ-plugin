package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.MatlabLanguage
import com.intellij.psi.tree.IElementType

class MatlabElementType(debugName: String) : IElementType(debugName, MatlabLanguage.INSTANCE)