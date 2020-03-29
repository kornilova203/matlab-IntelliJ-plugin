package com.github.kornilova203.matlab.refactoring

import com.github.kornilova203.matlab.psi.MatlabRefExpr
import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement

/**
 * @author Liudmila Kornilova
 **/
class MatlabRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(element: PsiElement, context: PsiElement?): Boolean {
        return element is MatlabRefExpr
    }
}
