package com.github.kornilova203.matlab.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.impl.source.tree.CompositePsiElement
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
open class MatlabASTWrapperPsiElement(node : ASTNode) : ASTWrapperPsiElement(node)  {
    override fun toString(): String {
        return "${javaClass.simpleName}(${node.elementType})"
    }
}
