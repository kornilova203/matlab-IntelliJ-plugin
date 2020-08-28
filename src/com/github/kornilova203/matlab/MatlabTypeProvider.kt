package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.MatlabAssignExpr
import com.github.kornilova203.matlab.psi.MatlabExpr
import com.github.kornilova203.matlab.psi.MatlabRefExpr
import com.intellij.lang.ExpressionTypeProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.SyntaxTraverser

class MatlabTypeProvider : ExpressionTypeProvider<MatlabRefExpr>() {
    override fun getInformationHint(element: MatlabRefExpr): String = (element.reference as MatlabReference).getType().getName()
    
    override fun getExpressionsAt(elementAt: PsiElement): MutableList<MatlabRefExpr> {
        return SyntaxTraverser.psiApi()
                .parents(elementAt)
                .takeWhile { it !is PsiFile }
                .filter(MatlabRefExpr::class.java)
                .toList()
    }

    override fun getErrorHint(): String = "No expression found"
}