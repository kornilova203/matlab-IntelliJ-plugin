package com.github.kornilova203.matlab.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabDeclarationBase(elementType: IElementType) : MatlabCompositePsiElement(elementType), MatlabDeclaration {

    override val visibleOutsideFunction = true

    override fun getNameIdentifier(): PsiElement? = getChildOfType(MatlabTypes.IDENTIFIER)

    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement {
        nameIdentifier?.replace(createIdentifierFromText(project, name))
        return this
    }

    override fun getTextOffset(): Int = nameIdentifier?.node?.startOffset ?: super.getTextOffset()
}
