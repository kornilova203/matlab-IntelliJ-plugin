package com.github.kornilova203.matlab.psi.types

import com.github.kornilova203.matlab.psi.MatlabClassDeclaration
import com.github.kornilova203.matlab.psi.MatlabFunctionDeclaration
import com.github.kornilova203.matlab.psi.MatlabStubbedFunctionDeclaration
import com.github.kornilova203.matlab.psi.isConstructor
import com.intellij.psi.util.parentOfTypes

class MatlabTypeFunction(val decl: MatlabStubbedFunctionDeclaration) : MatlabType {
    override fun getName(): String = "Function"

    fun getDeclaration(): MatlabFunctionDeclaration = decl
    fun getReturnType(): MatlabType {
        val cl = decl.isConstructor() ?: isClassReturn()
        return if (cl != null) MatlabTypeClass(cl) else MatlabTypeUnknown()
    }

    private fun isClassReturn(): MatlabClassDeclaration? {
        val classDeclaration = decl.parentOfTypes(MatlabClassDeclaration::class) ?: return null
        val retValues = decl.returnValues?.retValueList
        if (retValues?.size == 1) {
            val retValue = retValues[0].text
            val parameterList = decl.parameters?.parameterList
            val leftParameter = parameterList?.get(0)?.text
            return if (retValue == leftParameter) classDeclaration else null
        }
        return null
    }
}