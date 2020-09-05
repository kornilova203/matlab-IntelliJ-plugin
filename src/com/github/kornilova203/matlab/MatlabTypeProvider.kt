package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.*
import com.github.kornilova203.matlab.psi.types.MatlabTypeUnknown
import com.intellij.lang.ExpressionTypeProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.SyntaxTraverser

class MatlabTypeProvider : ExpressionTypeProvider<PsiElement>() {
    override fun getInformationHint(element: PsiElement): String {
        val type = if (element is MatlabTypedExpr) element.getType() else MatlabTypeUnknown()
        return type.getName()
    }

    override fun getExpressionsAt(elementAt: PsiElement): MutableList<PsiElement> {
        return SyntaxTraverser.psiApi()
                .parents(elementAt)
                .takeWhile { it !is PsiFile }
                .filter { it is MatlabTypedExpr && it !is MatlabAssignExpr &&
                        !(it is MatlabRefExpr && it.parent is MatlabFunctionExpr) }
                .toList()
    }

    override fun getErrorHint(): String = "No expression found"
}