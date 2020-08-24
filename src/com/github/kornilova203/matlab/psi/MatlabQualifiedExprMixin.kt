package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.psi.impl.MatlabBinaryExprImpl
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.parentOfTypes

abstract class MatlabQualifiedExprMixin(node: ASTNode) : MatlabBinaryExprImpl(node), MatlabQualifiedExpr {
    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        if (lastParent == this.right) {
            val classDeclaration = resolveQualified() ?: return false

            val declarations = PsiTreeUtil.findChildrenOfAnyType(classDeclaration, false,
                    MatlabProperty::class.java,
                    MatlabFunctionDeclaration::class.java,
                    MatlabEnumItem::class.java)
            
            for (element in declarations) {
                if (!processor.execute(element, state)) {
                    break
                }
            }
            return false
        }
        return true
    }
   
    private fun resolveQualified(): MatlabClassDeclaration? {
        var element = this.firstChild
        if (element is MatlabQualifiedExpr) {
            element = element.lastChild
        }
        return findClassDeclaration(element)
    }
}