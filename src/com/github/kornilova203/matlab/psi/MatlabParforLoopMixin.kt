package com.github.kornilova203.matlab.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.util.PsiTreeUtil

abstract class MatlabParforLoopMixin(node: ASTNode) : MatlabASTWrapperPsiElement(node), MatlabParforLoop {
    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        val interval = PsiTreeUtil.getChildOfType(this, MatlabParforLoopRange::class.java) ?: return true
        val assignExpr = PsiTreeUtil.getChildOfType(interval, MatlabAssignExpr::class.java) ?: return true
        return processor.execute(assignExpr, state)
    }
}