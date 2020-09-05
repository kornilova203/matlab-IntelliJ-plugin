package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.psi.types.MatlabType

interface MatlabTypedExpr {
    fun getType(): MatlabType
}