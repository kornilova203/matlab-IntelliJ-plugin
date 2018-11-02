package com.github.korniloval.matlab.highlighting

import com.github.korniloval.matlab.psi.MatlabClassDeclaration
import com.github.korniloval.matlab.psi.MatlabFunctionDeclaration
import com.github.korniloval.matlab.psi.MatlabRef
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
    }

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        psiTraverser().withRoot(element).forEach {
            if (it is MatlabRef) {
                when (it.parent) {
                    is MatlabFunctionDeclaration -> holder.createInfoAnnotation(it, null).textAttributes = FUNCTION_DECLARATION
                    is MatlabClassDeclaration -> holder.createInfoAnnotation(it, null).textAttributes = CLASS_DECLARATION
                }
            }
        }
    }
}
