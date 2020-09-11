package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.psi.types.MatlabType
import com.github.kornilova203.matlab.psi.types.MatlabTypeClass
import com.github.kornilova203.matlab.psi.types.MatlabTypeFunction
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
        val type = this.parentOfTypes(MatlabStubbedFunctionDeclaration::class)?.getType()
        return if (type is MatlabTypeFunction) type.getReturnType() else MatlabTypeUnknown()
    }
}