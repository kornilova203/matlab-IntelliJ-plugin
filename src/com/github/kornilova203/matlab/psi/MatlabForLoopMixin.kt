package com.github.kornilova203.matlab.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabForLoopMixin(elementType: IElementType) : MatlabCompositePsiElement(elementType), MatlabForLoop {
    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        val interval = PsiTreeUtil.getChildOfType(this, MatlabForLoopRange::class.java) ?: return true
        val assignExpr = PsiTreeUtil.getChildOfType(interval, MatlabAssignExpr::class.java) ?: return true
        return processor.execute(assignExpr, state)
    }
}