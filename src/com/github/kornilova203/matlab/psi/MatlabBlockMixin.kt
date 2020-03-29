package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.processDeclarations
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabBlockMixin(elementType: IElementType) : MatlabCompositePsiElement(elementType), MatlabBlock {
    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        processDeclarations(this, processor, state, lastParent, place)
        return true
    }
}
