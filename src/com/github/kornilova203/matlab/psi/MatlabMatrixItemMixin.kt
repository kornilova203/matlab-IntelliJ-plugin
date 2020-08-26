package com.github.kornilova203.matlab.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class MatlabMatrixItemMixin(node: ASTNode): MatlabDeclarationBase(node), MatlabMatrixItem {
    override val visibleOutsideFunction = false

    override fun getNameIdentifier(): PsiElement? {
        (firstChild as? MatlabRefExpr)?.let { return it.identifier() }
        return null
    }
}