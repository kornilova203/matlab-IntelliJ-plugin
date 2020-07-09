package com.github.kornilova203.matlab.highlighting

import com.github.kornilova203.matlab.MatlabElementTypes.*
import com.github.kornilova203.matlab.psi.MatlabClassDeclaration
import com.github.kornilova203.matlab.psi.MatlabFunctionDeclaration
import com.github.kornilova203.matlab.psi.MatlabLambdaExpr
import com.github.kornilova203.matlab.psi.MatlabParameters
import com.github.kornilova203.matlab.psi.MatlabTypes.*
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
        val ID = createTextAttributesKey("MATLAB.ID", DefaultLanguageHighlighterColors.IDENTIFIER)
    }

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        psiTraverser().withRoot(element).forEach { el ->
            val type = el.node.elementType
            val attrs = when {
                type == AT && el.parent is MatlabLambdaExpr -> LAMBDA_PARENTH
                (type == LPARENTH || type == RPARENTH) &&
                        el.parent is MatlabParameters && el.parent.parent is MatlabLambdaExpr  -> LAMBDA_PARENTH
                type == IDENTIFIER && el.parent is MatlabFunctionDeclaration                   -> FUNCTION_DECLARATION
                type == IDENTIFIER && el.parent is MatlabClassDeclaration                      -> CLASS_DECLARATION
                type == METHODS || type == EVENTS || type == PROPERTIES || type == ENUMERATION -> KEYWORD
                type == IDENTIFIER                                                             -> ID
                else -> null
            }
            if (attrs != null) {
                holder.createInfoAnnotation(el, null).textAttributes = attrs
            }
        }
    }
}
