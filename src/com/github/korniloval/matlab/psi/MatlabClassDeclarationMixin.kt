package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.psi.impl.MatlabClassDeclarationImpl
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabClassDeclarationMixin(elementType: IElementType) : MatlabCompositePsiElement(elementType), MatlabDeclaration, MatlabClassDeclaration {
    override fun getIdentifier(): MatlabRef? = (this as? MatlabClassDeclarationImpl)?.ref

    override val implicit: Boolean = false
}
