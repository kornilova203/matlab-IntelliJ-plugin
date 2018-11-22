package com.github.korniloval.matlab.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.search.SearchScope
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabRefMixin(elementType: IElementType) : MatlabCompositePsiElement(elementType), MatlabRef, PsiNameIdentifierOwner {

    override fun setName(name: String): PsiElement {
        val ref = MatlabPsiUtil.createRefFromText(project, name)
        identifier.replace(ref.identifier)
        return this
    }

    override fun getUseScope(): SearchScope {
        return super.getUseScope()
    }

    override fun getReference(): MatlabReference? {
        return MatlabReference(this)
    }

    override fun getName(): String? = text

    override fun getNameIdentifier(): PsiElement = identifier
}
