package com.github.kornilova203.matlab.psi

import com.intellij.psi.PsiNameIdentifierOwner

/**
 * @author Liudmila Kornilova
 **/
interface MatlabDeclaration : PsiNameIdentifierOwner {
    val visibleOutsideFunction: Boolean
    val visibleBeforeDeclaration: Boolean
        get() = false
}