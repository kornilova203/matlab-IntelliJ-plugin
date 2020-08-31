package com.github.kornilova203.matlab.completion

import com.github.kornilova203.matlab.psi.MatlabDeclaration
import com.github.kornilova203.matlab.psi.MatlabFunctionDeclaration
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.TextRange
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
        result.addElement(element)
        return true
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
    }
}

fun createFunctionLookupElement(declaration: MatlabDeclaration): LookupElementBuilder {
    val name = declaration.name
    if (name != null) {
        return LookupElementBuilder.create("$name()").withIcon(AllIcons.Nodes.Function).withInsertHandler { context, _ ->
            val caretModel = context.editor.caretModel
            val offset = caretModel.offset
            val document = context.document
            if (document.getText(TextRange(offset, offset + 2)) == "()") {
                document.deleteString(offset, offset + 2)
            }
            if (declaration !is MatlabFunctionDeclaration) {
                return@withInsertHandler
            }
            val parameterList = declaration.parameters?.parameterList ?: return@withInsertHandler
            if (parameterList.isNotEmpty()) {
                if (offset > 0) {
                    caretModel.moveToOffset(offset - 1)
                }
            }
        }
    }
    return LookupElementBuilder.create(declaration).withIcon(AllIcons.Nodes.Function)
}
