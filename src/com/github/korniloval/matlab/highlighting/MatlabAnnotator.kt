package com.github.korniloval.matlab.highlighting

import com.github.korniloval.matlab.MatlabElementTypes.*
import com.github.korniloval.matlab.psi.MatlabClassDeclaration
import com.github.korniloval.matlab.psi.MatlabFunctionDeclaration
import com.github.korniloval.matlab.psi.MatlabLambdaExpr
import com.github.korniloval.matlab.psi.MatlabParameters
import com.github.korniloval.matlab.psi.MatlabTypes.*
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.SyntaxTraverser.psiTraverser

/**
 * @author Liudmila Kornilova
 **/
class MatlabAnnotator : Annotator {
    companion object {
        val FUNCTION_DECLARATION = createTextAttributesKey("MATLAB.FUNC_DECLARATION", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION)
        val CLASS_DECLARATION = createTextAttributesKey("MATLAB.CLASS_DECLARATION", DefaultLanguageHighlighterColors.CLASS_NAME)
        val LAMBDA_PARENTH = createTextAttributesKey("MATLAB.LAMBDA_PARENTH", DefaultLanguageHighlighterColors.METADATA)
        val KEYWORD = createTextAttributesKey("MATLAB.KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
    }

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        psiTraverser().withRoot(element).forEach { el ->
            val type = el.node.elementType
            if (type == AT && el.parent is MatlabLambdaExpr) {
                holder.createInfoAnnotation(el as PsiElement, null).textAttributes = LAMBDA_PARENTH
            } else if (type == LPARENTH || type == RPARENTH) {
                if (el.parent is MatlabParameters && el.parent.parent is MatlabLambdaExpr) {
                    holder.createInfoAnnotation(el as PsiElement, null).textAttributes = LAMBDA_PARENTH
                }
            } else if (type == IDENTIFIER) {
                if (el.parent is MatlabFunctionDeclaration) holder.createInfoAnnotation(el, null).textAttributes = FUNCTION_DECLARATION
                if (el.parent is MatlabClassDeclaration) holder.createInfoAnnotation(el, null).textAttributes = CLASS_DECLARATION
            } else if (type == METHODS || type == EVENTS || type == PROPERTIES) {
                holder.createInfoAnnotation(el, null).textAttributes = KEYWORD
            }
        }
    }
}
