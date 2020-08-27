package com.github.kornilova203.matlab.completion

import com.github.kornilova203.matlab.MatlabLanguage
import com.github.kornilova203.matlab.editor.actions.reduceIndent
import com.github.kornilova203.matlab.psi.*
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.StandardPatterns.*
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement
import com.intellij.util.DocumentUtil
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
        private val IN_CLASS = and(M, psiElement().withSuperParent(2, MatlabClassDeclaration::class.java))
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
        
        extend(CompletionType.BASIC, 
                psiElement().and(IN_CLASS), 
                provider("properties", "methods", "events", "enumeration", "end"))
        
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
                    result.addElement(PrioritizedLookupElement.withPriority(LookupElementBuilder.create(keyword).bold().withInsertHandler { insertionContext, _ ->
                        val editor = insertionContext.editor
                        reduceIndent(insertionContext.project, editor, insertionContext.file)
                        if (insertionContext.completionChar == '\n' && keyword == "end") {
                            val document = editor.document
                            val offset = editor.caretModel.offset
                            val indent = DocumentUtil.getIndent(document, offset)
                            val end = DocumentUtil.getLineEndOffset(offset, document)
                            if (document.getText(TextRange(offset, end)).isBlank()) {
                                document.insertString(offset, "\n" + indent)
                                editor.caretModel.moveToOffset(offset + 1 + indent.length)
                                PsiDocumentManager.getInstance(insertionContext.project).commitDocument(document)
                            }
                        }
                    }, 1.0))
                }
            }
        }
    }
}