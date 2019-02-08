package com.github.korniloval.matlab.refactoring

import com.github.korniloval.matlab.psi.MatlabRefExpr
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
