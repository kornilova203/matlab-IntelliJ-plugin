package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.MatlabLanguage
import com.intellij.psi.tree.IElementType

class MatlabElementType(debugName: String) : IElementType(debugName, MatlabLanguage.INSTANCE)