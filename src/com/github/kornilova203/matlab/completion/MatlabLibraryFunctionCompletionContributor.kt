package com.github.kornilova203.matlab.completion

import com.github.kornilova203.matlab.completion.MatlabKeywordCompletionContributor.Companion.M
import com.github.kornilova203.matlab.psi.MatlabQualifiedExpr
import com.github.kornilova203.matlab.psi.MatlabRefExpr
import com.github.kornilova203.matlab.psi.MatlabTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.StandardPatterns.*
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import java.io.BufferedReader
import java.nio.file.Paths
import kotlin.collections.HashSet

class MatlabLibraryFunctionCompletionContributor : CompletionContributor() {
    companion object {
        private var functions = HashSet<LookupElement>()
        private var packages = HashMap<String, MutableSet<LookupElement>>()

        val KEYWORDS = hashSetOf("function", "end", "if", "else", "elseif", "while", "switch", "case", "otherwise", "for", "classdef", "try", "catch", "global")

        private val IDENT = psiElement(MatlabTypes.IDENTIFIER).withParent(MatlabRefExpr::class.java)
        private val IN_QUALIFIED_EXPR = and(M, IDENT.withSuperParent(2, MatlabQualifiedExpr::class.java))
        private val AFTER_NUMBER_LITERAL = psiElement().afterLeafSkipping(
                alwaysFalse<PsiElement>(),
                or(psiElement(MatlabTypes.INTEGER), psiElement(MatlabTypes.FLOAT))
        )
    }

    init {
        getDocs()
        extend(CompletionType.BASIC,
                M,
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet) {
                        val element = parameters.position
                        if (AFTER_NUMBER_LITERAL.accepts(element)) {
                            result.stopHere()
                            return
                        }
                        if (IN_QUALIFIED_EXPR.accepts(element)) {
                            val valueBeforePoint = element.parent.prevSibling.prevSibling.lastChild.text
                            if (packages.containsKey(valueBeforePoint)) {
                                result.addAllElements(packages[valueBeforePoint]!!)
                            }
                        } else {
                            result.addAllElements(functions)
                        }
                    }
                })
    }

    private fun getDocs() {
        val stream = javaClass.classLoader.getResourceAsStream("docs/function_list") ?: return
        BufferedReader(stream.reader()).forEachLine { line ->
            parseName(line)
        }
    }

    private fun parseName(name: String) {
        if (KEYWORDS.contains(name)) {
            return
        }
        if (name.contains('.')) {
            val names = name.split('.')
            for (j in 0 until names.size - 1) {
                functions.add(packageElement(names[j]))
                val nextElement = if (j + 1 == names.size - 1) funcElement(names[j + 1]) else packageElement(names[j + 1])
                if (packages.containsKey(names[j])) {
                    packages[names[j]]?.add(nextElement)
                } else {
                    packages[names[j]] = mutableSetOf<LookupElement>(nextElement)
                }
            }
            functions.add(funcElement(names.last()))
        } else {
            functions.add(funcElement(name))
        }
    }

    private fun funcElement(name: String) = LookupElementBuilder.create(name).withIcon(AllIcons.Nodes.Function)

    private fun packageElement(name: String) = LookupElementBuilder.create(name).withIcon(AllIcons.Nodes.Package)
}


