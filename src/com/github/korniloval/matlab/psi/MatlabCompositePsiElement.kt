package com.github.korniloval.matlab.psi

import com.intellij.psi.impl.source.tree.CompositePsiElement
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
open class MatlabCompositePsiElement(type: IElementType) : CompositePsiElement(type) {
    override fun toString(): String {
        return "${javaClass.simpleName}($elementType)"
    }
}
