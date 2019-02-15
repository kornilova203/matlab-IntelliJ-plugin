package com.github.korniloval.matlab.psi

import com.intellij.psi.PsiNameIdentifierOwner

/**
 * @author Liudmila Kornilova
 **/
interface MatlabDeclaration : PsiNameIdentifierOwner {
    val visibleOutsideFunction: Boolean
}