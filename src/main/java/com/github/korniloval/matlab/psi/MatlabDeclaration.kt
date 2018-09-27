package com.github.korniloval.matlab.psi

/**
 * @author Liudmila Kornilova
 **/
interface MatlabDeclaration {
    fun getIdentifier(): MatlabRef?

    val implicit: Boolean
}
