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
        val stubs = mutableListOf<LookupElement>()
        resolveIndex(project, MatlabClassDeclarationIndex.KEY, MatlabClassDeclaration::class.java, stubs) { declaration ->
            return@resolveIndex LookupElementBuilder.create(declaration).withIcon(AllIcons.Nodes.Class)
        }
        resolveIndex(project, MatlabFunctionDeclarationIndex.KEY, MatlabFunctionDeclaration::class.java, stubs) { declaration -> createFunctionLookupElement(declaration) }
        result.addAllElements(stubs)
    }

    private fun <Psi : PsiElement?> resolveIndex(project: Project, indexKey: StubIndexKey<String, Psi>, requiredClass: Class<Psi>, stubs: MutableList<LookupElement>, create: (MatlabDeclaration) -> LookupElement?) {
        StubIndex.getInstance().processAllKeys(indexKey, project) { key ->
            StubIndex.getInstance().processElements(indexKey, key, project, GlobalSearchScope.projectScope(project), requiredClass) { psiElement ->
                if (psiElement is MatlabDeclaration && psiElement.name + ".m" == psiElement.containingFile.name) {
                    val element = create(psiElement)
                    if (element != null) {
                        stubs.add(element)
                    }
                }
                return@processElements true
            }
        }
    }
}