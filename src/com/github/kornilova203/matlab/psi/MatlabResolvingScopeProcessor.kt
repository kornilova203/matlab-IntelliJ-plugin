package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.MatlabReference
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

/**
 * @author Liudmila Kornilova
 **/
class MatlabResolvingScopeProcessor(private val myReference: MatlabReference) : PsiScopeProcessor {
    var declaration: MatlabDeclaration? = null

    override fun execute(decl: PsiElement, state: ResolveState): Boolean {
        if (decl !is MatlabDeclaration) return true
        if (decl.name == myReference.element.text) {
            this.declaration = decl
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
