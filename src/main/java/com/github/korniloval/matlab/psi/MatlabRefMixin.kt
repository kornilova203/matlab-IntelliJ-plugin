package com.github.korniloval.matlab.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.util.PsiTreeUtil

/**
 * @author Liudmila Kornilova
 **/
open class MatlabRefMixin(node: ASTNode) : ASTWrapperPsiElement(node) {
    override fun getReference(): MatlabReference? {
        val containingDeclaration = PsiTreeUtil.findFirstParent(this) { it is MatlabDeclaration } as? MatlabDeclaration
        return MatlabReference(this, containingDeclaration?.implicit ?: true)
    }
}
