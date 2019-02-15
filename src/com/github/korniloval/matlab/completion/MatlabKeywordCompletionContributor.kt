package com.github.korniloval.matlab.completion

import com.github.korniloval.matlab.MatlabLanguage
import com.github.korniloval.matlab.psi.MatlabBlock
import com.github.korniloval.matlab.psi.MatlabFile
import com.github.korniloval.matlab.psi.MatlabRefExpr
import com.github.korniloval.matlab.psi.MatlabTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.StandardPatterns.and
import com.intellij.util.ProcessingContext

class MatlabKeywordCompletionContributor : CompletionContributor() {

    companion object {
        val M = psiElement().withLanguage(MatlabLanguage.INSTANCE)
        private val IDENT = psiElement(MatlabTypes.IDENTIFIER).withParent(MatlabRefExpr::class.java)
        private val AT_TOP_LEVEL = and(M, IDENT.withSuperParent(2, MatlabFile::class.java))
        private val IN_BLOCK = and(M, IDENT.withSuperParent(2, MatlabBlock::class.java))
    }

    init {
        extend(CompletionType.BASIC,
                psiElement().andOr(AT_TOP_LEVEL, IN_BLOCK),
                provider("function", "if", "while", "for", "classdef"))

        extend(CompletionType.BASIC,
                psiElement().and(IN_BLOCK),
                provider("end"))

        // todo: completion in classdef
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