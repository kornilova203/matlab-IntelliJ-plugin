package com.github.kornilova203.matlab.completion

import com.github.kornilova203.matlab.completion.MatlabKeywordCompletionContributor.Companion.M
import com.github.kornilova203.matlab.psi.MatlabQualifiedExpr
import com.github.kornilova203.matlab.psi.MatlabRefExpr
import com.github.kornilova203.matlab.psi.MatlabTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.StandardPatterns.*
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import java.io.BufferedReader
import javax.xml.parsers.DocumentBuilderFactory
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
        try {
            val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("https://www.mathworks.com/help/matlab/referencelist_function_cat.xml")
            val refs = doc.getElementsByTagName("ref")
            for (i in 0 until refs.length) {
                val name = refs.item(i).attributes.getNamedItem("name").nodeValue
                if (name.matches("^[a-zA-Z0-9.]*$".toRegex())) {
                    parseName(name)
                }
            }
        } catch (e: Exception) {
            val stream = javaClass.classLoader.getResourceAsStream("/docs/function_list") ?: return
            BufferedReader(stream.reader()).use { reader ->
                var line = reader.readLine()
                while (line != null) {
                    parseName(line)
                    line = reader.readLine()
                }
            }
        }

    }

    private fun parseName(name: String) {
        if (KEYWORDS.contains(name)) {
            return
        }
        if (name.contains('.')) {
            val names = name.split('.')
            functions.add(LookupElementBuilder.create(names[0]))
            for (j in 1 until names.size) {
                functions.add(LookupElementBuilder.create(names[j]))
                if (packages.containsKey(names[j - 1])) {
                    packages[names[j - 1]]?.add(LookupElementBuilder.create(names[j]))
                } else {
                    packages[names[j - 1]] = mutableSetOf<LookupElement>(LookupElementBuilder.create(names[j]))
                }
            }
        } else {
            functions.add(LookupElementBuilder.create(name))
        }
    }
}


