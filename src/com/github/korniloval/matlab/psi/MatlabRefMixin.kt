package com.github.korniloval.matlab.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabRefMixin(node: ASTNode) : ASTWrapperPsiElement(node), MatlabRef, PsiNameIdentifierOwner {

    override fun setName(name: String): PsiElement {
        val ref = MatlabPsiUtil.createRefFromText(project, name)
        return this.replace(ref)
    }

    override fun getReference(): MatlabReference? {
        return MatlabReference(this)
    }

    override fun getName(): String? = text

    override fun getNameIdentifier(): PsiElement? = this
}
