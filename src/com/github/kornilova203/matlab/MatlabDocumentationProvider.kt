package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.*
import com.intellij.lang.documentation.DocumentationMarkup.*
import com.intellij.lang.documentation.DocumentationProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import java.lang.StringBuilder
import java.util.*

class MatlabDocumentationProvider : DocumentationProvider {
    override fun getQuickNavigateInfo(element: PsiElement?, originalElement: PsiElement?): String? {
        return getDeclaration(element, originalElement)
    }

    override fun getUrlFor(element: PsiElement?, originalElement: PsiElement?): List<String?>? {
        val stream = javaClass.classLoader.getResourceAsStream("docs/function_url.properties")
        val properties = Properties()
        properties.load(stream)
        val url = properties.getProperty(originalElement?.text) ?: return emptyList()
        return listOf("https://www.mathworks.com/help/matlab/$url")
    }

    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        val declaration = getDeclaration(element, originalElement) ?: originalElement?.text ?: ""
        val result = DEFINITION_START + declaration + DEFINITION_END
        val stream = javaClass.classLoader.getResourceAsStream("docs/function_descriptions.properties")
        val properties = Properties()
        properties.load(stream)
        val description = properties.getProperty(originalElement?.text)
        if (description != null) {
            return result + CONTENT_START + description + CONTENT_END
        }
        if (element is MatlabFunctionDeclaration || element is MatlabClassDeclaration) {
            var comment: PsiElement? = element.getChildOfType(MatlabTypes.COMMENT) ?: return result
            val commentText = StringBuilder()
            while (comment != null && comment.elementType == MatlabTypes.COMMENT) {
                commentText.append(" ", printComment(comment))
                if (comment.nextSibling.elementType == MatlabTypes.NEWLINE) {
                    comment = PsiTreeUtil.skipWhitespacesForward(comment.nextSibling)
                } else {
                    break
                }
            }
            return result + CONTENT_START + commentText.removePrefix(" ") + CONTENT_END
        }
        return result
    }
    
    private fun getDeclaration(element: PsiElement?, originalElement: PsiElement?): String? {
        when (element) {
            is MatlabAssignExpr -> return element.text
            is MatlabGlobalVariableDeclaration -> return "global ${originalElement?.text}"
            is MatlabParameter -> {
                val function = element.parent.parent
                val functionName = if (function is MatlabFunctionDeclaration) getName(function) else ""
                return "parameter '${element.text}' of function '${functionName}'"
            }
            is MatlabRetValue -> {
                val function = element.parent.parent
                val functionName = if (function is MatlabFunctionDeclaration) getName(function) else ""
                return "return value '${element.text}' of function '${functionName}'"
            }
            is MatlabFunctionDeclaration -> {
                val result = StringBuilder("function ")
                result.append(printElement(element.returnValues, "", " = "))
                result.append(printElement(element.getterOrSetterModifier, "", ""))
                result.append(getName(element))
                result.append(printElement(element.parameters, "", ""))
                return result.toString()
            }
            is MatlabClassDeclaration -> {
                val result = StringBuilder("classdef ")
                result.append(printElement(element.attributes, "", " "))
                result.append(getName(element))
                result.append(printElement(element.superClasses, " &lt ", ""))
                return result.toString()
            }
        }
        return null
    }

    private fun getName(element: PsiElement?): String {
        val ident = element?.getChildOfType(MatlabTypes.IDENTIFIER) ?: return ""
        return ident.text
    }

    private fun printElement(element: PsiElement?, prefix: String, suffix: String): String {
        return if (element == null) "" else prefix + element.text + suffix
    }

    private fun printComment(comment: PsiElement?): String {
        comment ?: return ""
        return comment.text.removePrefix("%").removePrefix("%")
    }
}