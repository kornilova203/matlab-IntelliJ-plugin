package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.psi.types.MatlabType
import com.github.kornilova203.matlab.psi.types.MatlabTypeClass
import com.github.kornilova203.matlab.psi.types.MatlabTypeUnknown
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.util.parentOfTypes

abstract class MatlabParameterMixin(node: ASTNode) : MatlabASTWrapperPsiElement(node), MatlabParameter, MatlabDeclaration, MatlabTypedExpr {
    override val visibleOutsideFunction = true

    override fun getNameIdentifier(): PsiElement? = getChildOfType(MatlabTypes.IDENTIFIER)

    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement {
        nameIdentifier?.replace(createIdentifierFromText(project, name))
        return this
    }

    override fun getTextOffset(): Int = nameIdentifier?.node?.startOffset ?: super.getTextOffset()

    override fun getType(): MatlabType {
        val cl = isFirstParameter()
        return if (cl != null) MatlabTypeClass(cl) else MatlabTypeUnknown()
    }

    private fun isFirstParameter(): MatlabClassDeclaration? {
        val parametersList = this.parentOfTypes(MatlabParametersList::class) ?: return null
        val list = parametersList.parameterList
        if (list.size == 0 || list[0] != this) {
            return null
        }
        return this.parentOfTypes(MatlabClassDeclaration::class)
    }
}