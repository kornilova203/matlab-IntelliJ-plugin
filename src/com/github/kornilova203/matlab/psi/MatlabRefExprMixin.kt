package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.MatlabReference
import com.intellij.lang.ASTNode

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabRefExprMixin(node: ASTNode) : MatlabASTWrapperPsiElement(node), MatlabRefExpr {
    override fun getReference(): MatlabReference = MatlabReference(this)

    override fun getName(): String? = text
}
