package com.github.korniloval.matlab.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabRefMixin(node: ASTNode) : ASTWrapperPsiElement(node), MatlabRef, PsiNamedElement {

    override fun setName(name: String): PsiElement {
        // todo
        return this
    }

    override fun getReference(): MatlabReference? {
        return MatlabReference(this)
    }

    override fun getName(): String? = text
}
