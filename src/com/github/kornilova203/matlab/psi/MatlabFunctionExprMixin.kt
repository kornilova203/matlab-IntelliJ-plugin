package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.psi.types.MatlabType
import com.github.kornilova203.matlab.psi.types.MatlabTypeUnknown
import com.intellij.lang.ASTNode

abstract class MatlabFunctionExprMixin(node: ASTNode) : MatlabASTWrapperPsiElement(node), MatlabFunctionExpr, MatlabTypedExpr {
    override fun getType(): MatlabType { 
        val expr = this.expr
        return if (expr is MatlabTypedExpr) expr.getType() else MatlabTypeUnknown()
    }
}