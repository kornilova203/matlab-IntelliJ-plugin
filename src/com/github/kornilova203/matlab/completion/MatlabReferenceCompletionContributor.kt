package com.github.kornilova203.matlab.completion

import com.github.kornilova203.matlab.completion.MatlabKeywordCompletionContributor.Companion.M
import com.github.kornilova203.matlab.psi.MatlabClassDeclaration
import com.github.kornilova203.matlab.psi.MatlabDeclaration
import com.github.kornilova203.matlab.psi.MatlabFunctionDeclaration
import com.github.kornilova203.matlab.stub.MatlabClassDeclarationIndex
import com.github.kornilova203.matlab.stub.MatlabFunctionDeclarationIndex
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.stubs.StubIndexKey
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext

class MatlabReferenceCompletionContributor : CompletionContributor() {
    val stubs = mutableListOf<LookupElement>()

    init {
        extend(CompletionType.BASIC,
                M,
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet) {
                        val processor = MatlabCompletionScopeProcessor(result)
                        val position = parameters.originalPosition ?: return
                        PsiTreeUtil.treeWalkUp(processor, position, parameters.originalFile, ResolveState.initial())
                        addCompletionsFromStubs(position, result)
                    }
                })
    }

    private fun addCompletionsFromStubs(position: PsiElement, result: CompletionResultSet) {
        val project = position.project
        resolveIndex(project, MatlabClassDeclarationIndex.KEY, MatlabClassDeclaration::class.java) { declaration ->
            return@resolveIndex LookupElementBuilder.create(declaration).withIcon(AllIcons.Nodes.Class)
        }
        resolveIndex(project, MatlabFunctionDeclarationIndex.KEY, MatlabFunctionDeclaration::class.java) { declaration ->
            val name = declaration.name
            if (name != null) {
                return@resolveIndex LookupElementBuilder.create("$name()").withIcon(AllIcons.Nodes.Function).withInsertHandler { context, _ ->
                    if (declaration !is MatlabFunctionDeclaration) {
                        return@withInsertHandler
                    }
                    val parameterList = declaration.parameters?.parameterList ?: return@withInsertHandler
                    if (parameterList.isNotEmpty()) {
                        val caretModel = context.editor.caretModel
                        val offset = caretModel.offset
                        if (offset > 0) {
                            caretModel.moveToOffset(offset - 1)
                        }
                    }
                }
            }
            return@resolveIndex LookupElementBuilder.create(declaration).withIcon(AllIcons.Nodes.Function)
        }
        result.addAllElements(stubs)
        stubs.clear()
    }

    private fun <Psi : PsiElement?> resolveIndex(project: Project, indexKey: StubIndexKey<String, Psi>, requiredClass: Class<Psi>, create: (MatlabDeclaration) -> LookupElement) {
        StubIndex.getInstance().processAllKeys(indexKey, project) { key ->
            StubIndex.getInstance().processElements(indexKey, key, project, GlobalSearchScope.projectScope(project), requiredClass) { psiElement ->
                if (psiElement is MatlabDeclaration && psiElement.name + ".m" == psiElement.containingFile.name) {
                    stubs.add(create(psiElement))
                }
                return@processElements true
            }
        }
    }
}