package com.github.kornilova203.matlab.refactoring

import com.github.kornilova203.matlab.psi.MatlabRefExpr
import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement
import com.intellij.refactoring.RefactoringActionHandler

/**
 * @author Liudmila Kornilova
 **/
class MatlabRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(element: PsiElement, context: PsiElement?): Boolean {
        return element is MatlabRefExpr
    }

    override fun getIntroduceVariableHandler(): RefactoringActionHandler? {
        return MatlabIntroduceVariableHandler()
    }
}
