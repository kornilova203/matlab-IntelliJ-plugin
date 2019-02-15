package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.psi.impl.MatlabBinaryExprImpl
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabAssignExprMixin(elementType: IElementType) : MatlabBinaryExprImpl(elementType), MatlabDeclaration, MatlabAssignExpr {

    override fun getNameIdentifier(): PsiElement? {
        (left as? MatlabRefExpr)?.let { return it.identifier }
        return null
    }

    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement {
        nameIdentifier?.replace(MatlabPsiUtil.createIdentifierFromText(project, name))
        return this
    }
}
