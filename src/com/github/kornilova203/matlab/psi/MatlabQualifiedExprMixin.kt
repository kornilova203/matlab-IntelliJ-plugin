package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.processDeclarationsInClass
import com.github.kornilova203.matlab.psi.impl.MatlabBinaryExprImpl
import com.github.kornilova203.matlab.psi.types.MatlabType
import com.github.kornilova203.matlab.psi.types.MatlabTypeClass
import com.github.kornilova203.matlab.psi.types.MatlabTypeUnknown
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.parentOfTypes

abstract class MatlabQualifiedExprMixin(node: ASTNode) : MatlabBinaryExprImpl(node), MatlabQualifiedExpr, MatlabTypedExpr {
    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        if (lastParent != this.right) {
            return true
        }
        val element = this.firstChild
        if (element !is MatlabTypedExpr) {
            return true
        }
        val type = element.getType()
        if (type !is MatlabTypeClass) {
            return true
        }
        return processDeclarationsInClass(processor, state, type)
    }

    override fun getType(): MatlabType {
        val expr = lastChild
        return if (expr is MatlabTypedExpr) expr.getType() else MatlabTypeUnknown()
    }
}