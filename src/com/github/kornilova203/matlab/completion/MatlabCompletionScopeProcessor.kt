package com.github.kornilova203.matlab.completion

import com.github.kornilova203.matlab.psi.MatlabDeclaration
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

    override fun execute(decl: PsiElement, state: ResolveState): Boolean {
        if (decl !is MatlabDeclaration) return true
        result.addElement(LookupElementBuilder.create(decl))
        return true
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
    }
}
