package com.github.kornilova203.matlab.psi

import com.intellij.codeInsight.PsiEquivalenceUtil
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

abstract class MatlabFunctionExprMixin(node: ASTNode) : MatlabASTWrapperPsiElement(node), MatlabFunctionExpr {
    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        if (!PsiEquivalenceUtil.areElementsEquivalent(this, place.parent)) {
            return true
        }
        val arguments = this.arguments.children
        if (arguments.isEmpty()) {
            return true
        }
        val classDeclaration = findClassDeclaration(arguments[0]) ?: return true
        val methods = classDeclaration.methodsBlockList
        for (method in methods) {
            val functionList = method.methodsList?.functionDeclarationList ?: return true
            for (function in functionList) {
                processor.execute(function, state)
            }
        }
        return (processor as MatlabResolvingScopeProcessor).declaration == null
    }
}