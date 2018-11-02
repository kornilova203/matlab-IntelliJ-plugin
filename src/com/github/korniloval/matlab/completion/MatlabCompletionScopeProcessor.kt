package com.github.korniloval.matlab.completion

import com.github.korniloval.matlab.psi.MatlabRefMixin
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

/**
 * @author Liudmila Kornilova
 **/
class MatlabCompletionScopeProcessor(private val result: CompletionResultSet) : PsiScopeProcessor {

    override fun execute(refInDeclaration: PsiElement, state: ResolveState): Boolean {
        if (refInDeclaration !is MatlabRefMixin) return true
        val resolve = refInDeclaration.reference?.resolve()
        if (resolve == null) result.addElement(LookupElementBuilder.create(refInDeclaration))
        else result.addElement(LookupElementBuilder.create(resolve))
        return true
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
    }
}
