package com.github.kornilova203.matlab.psi.types

import com.github.kornilova203.matlab.psi.MatlabClassDeclaration
import com.github.kornilova203.matlab.psi.MatlabDeclaration

class MatlabTypeClass(val decl: MatlabClassDeclaration): MatlabType {
    override fun getName(): String {
        return (decl as MatlabDeclaration).name ?: "<unknown>"
    }

    fun getDeclaration(): MatlabClassDeclaration = decl
}