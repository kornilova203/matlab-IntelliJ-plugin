package com.github.kornilova203.matlab.completion

import com.github.kornilova203.matlab.psi.MatlabDeclaration
import com.github.kornilova203.matlab.psi.MatlabFunctionDeclaration
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
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
        val element = if (decl is MatlabFunctionDeclaration) createFunctionLookupElement(decl) else LookupElementBuilder.create(decl)
        if (element != null) result.addElement(element)
        return true
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
    }
}

fun createFunctionLookupElement(name: String?, declaration: MatlabDeclaration? = null): LookupElementBuilder? {
    name ?: return null
    return LookupElementBuilder.create(name)
            .withTailText("()")
            .withPsiElement(declaration)
            .withIcon(AllIcons.Nodes.Function)
            .withInsertHandler(ParenthesesInsertHandler.WITH_PARAMETERS)
}

fun createFunctionLookupElement(declaration: MatlabDeclaration): LookupElementBuilder? {
    return createFunctionLookupElement(declaration.name, declaration)
}
