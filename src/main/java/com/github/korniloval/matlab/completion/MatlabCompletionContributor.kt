package com.github.korniloval.matlab.completion

import com.github.korniloval.matlab.MatlabLanguage
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.util.ProcessingContext

class MatlabCompletionContributor : CompletionContributor() {

    init {
        extend(CompletionType.BASIC,
                psiElement().withLanguage(MatlabLanguage.INSTANCE),
                provider("function", "end", "if", "else", "elseif", "while", "for", "classdef", "properties",
                        "methods", "events"))
    }

    private fun provider(vararg keywords: String): CompletionProvider<CompletionParameters> {
        return object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet) {
                for (keyword in keywords) {
                    result.addElement(LookupElementBuilder.create(keyword).bold())
                }
            }
        }
    }
}