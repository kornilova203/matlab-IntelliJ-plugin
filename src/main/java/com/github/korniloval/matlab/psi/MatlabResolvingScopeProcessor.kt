package com.github.korniloval.matlab.psi

import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

/**
 * @author Liudmila Kornilova
 **/
class MatlabResolvingScopeProcessor(private val myReference: MatlabReference) : PsiScopeProcessor {
    var declaration: MatlabRefMixin? = null
        private set(value) {
            if (declaration != null) {
                LOG.warn("Declaration for $this was already found: ${this.declaration}")
            }
            field = value
        }

    override fun execute(refInDeclaration: PsiElement, state: ResolveState): Boolean {
        if (refInDeclaration !is MatlabRefMixin) return true
        if (refInDeclaration.text == myReference.myElement.text) {
            this.declaration = refInDeclaration
            return false
        }
        return true
    }

    companion object {
        private val LOG = Logger.getInstance(MatlabReference::class.java)
    }
}
