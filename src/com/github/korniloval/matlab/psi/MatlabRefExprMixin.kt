package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.MatlabReference
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabRefExprMixin(elementType: IElementType) : MatlabCompositePsiElement(elementType), MatlabRefExpr {
    override fun getReference(): MatlabReference = MatlabReference(this)

    override fun getName(): String? = text
}
