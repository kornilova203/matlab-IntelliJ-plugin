package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.psi.MatlabTypes.IDENTIFIER
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabIdentifierTokenMixin(elementType: IElementType) : MatlabCompositePsiElement(elementType), MatlabIdentifierToken {
    override fun getElementType(): IElementType = IDENTIFIER
}
