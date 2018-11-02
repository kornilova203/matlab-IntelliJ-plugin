package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.psi.impl.MatlabClassDeclarationImpl
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabClassDeclarationMixin(node: ASTNode) : ASTWrapperPsiElement(node), MatlabDeclaration, MatlabClassDeclaration {
    override fun getIdentifier(): MatlabRef? = (this as? MatlabClassDeclarationImpl)?.ref

    override val implicit: Boolean = false
}
