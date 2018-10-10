package com.github.korniloval.matlab.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

/**
 * @author Liudmila Kornilova
 **/
open class MatlabBlockMixin(node: ASTNode) : ASTWrapperPsiElement(node), MatlabBlock {
    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        processDeclarations(this, processor, state)
        return true
    }
}