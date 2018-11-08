package com.github.korniloval.matlab.psi

import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

/**
 * @author Liudmila Kornilova
 **/
class MatlabResolvingScopeProcessor(private val myReference: MatlabReference) : PsiScopeProcessor {
    var declaration: MatlabRefMixin? = null

    override fun execute(refInDeclaration: PsiElement, state: ResolveState): Boolean {
        if (refInDeclaration !is MatlabRefMixin) return true
        if (refInDeclaration.text == myReference.element.text) {
            this.declaration = refInDeclaration
            return false
        }
        return true
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
    }
}
