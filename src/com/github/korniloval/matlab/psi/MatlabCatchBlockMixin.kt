package com.github.korniloval.matlab.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabCatchBlockMixin(elementType: IElementType) : MatlabDeclarationBase(elementType), MatlabCatchBlock {
    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        return processor.execute(this, state)
    }
}
