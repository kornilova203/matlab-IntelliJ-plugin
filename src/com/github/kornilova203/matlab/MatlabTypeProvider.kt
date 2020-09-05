package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.MatlabAssignExpr
import com.github.kornilova203.matlab.psi.MatlabExpr
import com.github.kornilova203.matlab.psi.MatlabRefExpr
import com.github.kornilova203.matlab.psi.MatlabTypedExpr
import com.github.kornilova203.matlab.psi.types.MatlabTypeUnknown
import com.intellij.lang.ExpressionTypeProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.SyntaxTraverser

class MatlabTypeProvider : ExpressionTypeProvider<MatlabExpr>() {
    override fun getInformationHint(element: MatlabExpr): String {
        val type = if (element is MatlabTypedExpr) element.getType() else MatlabTypeUnknown()
        return type.getName()
    }
    
    override fun getExpressionsAt(elementAt: PsiElement): MutableList<MatlabExpr> {
        return SyntaxTraverser.psiApi()
                .parents(elementAt)
                .takeWhile { it !is PsiFile }
                .filter(MatlabExpr::class.java) 
                .toList()
    }

    override fun getErrorHint(): String = "No expression found"
}