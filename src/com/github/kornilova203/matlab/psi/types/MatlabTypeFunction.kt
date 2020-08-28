package com.github.kornilova203.matlab.psi.types

import com.github.kornilova203.matlab.psi.MatlabFunctionDeclaration

class MatlabTypeFunction(val decl: MatlabFunctionDeclaration) : MatlabType {
    override fun getName(): String = "Function"

    fun getDeclaration(): MatlabFunctionDeclaration = decl
}