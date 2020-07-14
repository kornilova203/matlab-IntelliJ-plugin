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
        if (!state.get(MatlabReference.IS_ORIGINAL_FILE) && decl !is MatlabFunctionDeclaration && decl !is MatlabClassDeclaration) {
            return true
        }
        if (decl.name == myReference.element.text && (decl is MatlabGlobalVariableDeclaration || declaration == null || isResolvedToItself())) {
            this.declaration = decl
            return decl !is MatlabGlobalVariableDeclaration
        }
        return true
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
    }

    private fun isResolvedToItself(): Boolean {
        return declaration?.identifyingElement == myReference.element.identifier()
    }
}
