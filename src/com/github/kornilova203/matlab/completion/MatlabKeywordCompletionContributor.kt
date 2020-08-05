package com.github.kornilova203.matlab.completion

import com.github.kornilova203.matlab.MatlabLanguage
import com.github.kornilova203.matlab.psi.*
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.StandardPatterns.*
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext

class MatlabKeywordCompletionContributor : CompletionContributor() {

    companion object {
        val M = psiElement().withLanguage(MatlabLanguage.INSTANCE)
        private val IDENT = psiElement(MatlabTypes.IDENTIFIER).withParent(MatlabRefExpr::class.java)
        private val AT_TOP_LEVEL = and(M, IDENT.withSuperParent(2, MatlabFile::class.java))
        private val IN_BLOCK = and(M, IDENT.withSuperParent(2, MatlabBlock::class.java))
        private val AFTER_NUMBER_LITERAL = psiElement().afterLeafSkipping(
                alwaysFalse<PsiElement>(),
                or(psiElement(MatlabTypes.INTEGER), psiElement(MatlabTypes.FLOAT))
        )
        private val IN_CYCLE = and(M, or(IDENT.inside(MatlabWhileLoop::class.java), IDENT.inside(MatlabForLoop::class.java)))
    }

    init {
        extend(CompletionType.BASIC,
                psiElement().andOr(AT_TOP_LEVEL, IN_BLOCK),
                provider("function", "if", "while", "for", "classdef", "return"))

        extend(CompletionType.BASIC,
                psiElement().and(IN_BLOCK),
                provider("end"))

        extend(CompletionType.BASIC,
                psiElement().and(IN_CYCLE).and(IN_BLOCK),
                provider("continue", "break"))

        // todo: completion in classdef
    }

    private fun provider(vararg keywords: String): CompletionProvider<CompletionParameters> {
        return object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet) {
                val position = parameters.position
                if (AFTER_NUMBER_LITERAL.accepts(position)) {
                    result.stopHere()
                    return
                }
                for (keyword in keywords) {
                    result.addElement(LookupElementBuilder.create(keyword).bold())
                }
            }
        }
    }
}