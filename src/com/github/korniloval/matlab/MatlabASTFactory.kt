package com.github.korniloval.matlab

import com.github.korniloval.matlab.psi.MatlabTypes
import com.intellij.lang.ASTFactory
import com.intellij.psi.impl.source.tree.CompositeElement
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
class MatlabASTFactory : ASTFactory() {
    override fun createComposite(type: IElementType): CompositeElement? {
        return MatlabTypes.Factory.createElement(type)
    }
}
