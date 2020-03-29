package com.github.kornilova203.matlab.completion

import com.github.kornilova203.matlab.completion.MatlabKeywordCompletionContributor.Companion.M
import com.intellij.codeInsight.completion.*
import com.intellij.psi.ResolveState
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
                    }
                })
    }
}