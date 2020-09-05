package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.psi.types.MatlabType
import com.github.kornilova203.matlab.psi.types.MatlabTypeClass
import com.github.kornilova203.matlab.psi.types.MatlabTypeUnknown
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.util.parentOfTypes

abstract class MatlabRetValueMixin(node: ASTNode) : MatlabASTWrapperPsiElement(node), MatlabRetValue, MatlabDeclaration, MatlabTypedExpr {
    override val visibleOutsideFunction = true

    override fun getNameIdentifier(): PsiElement? = getChildOfType(MatlabTypes.IDENTIFIER)

    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement {
        nameIdentifier?.replace(createIdentifierFromText(project, name))
        return this
    }

    override fun getTextOffset(): Int = nameIdentifier?.node?.startOffset ?: super.getTextOffset()

    override fun getType(): MatlabType {
        val cl = isEqualFirstParameter()
        return if (cl != null) MatlabTypeClass(cl) else MatlabTypeUnknown()
    }

    private fun isEqualFirstParameter(): MatlabClassDeclaration? {
        val function = parentOfTypes(MatlabFunctionDeclaration::class) ?: return null
        val list = function.parameters?.parameterList ?: return null
        if (list.isEmpty() || list[0].text != text) {
            return null
        }
        return this.parentOfTypes(MatlabClassDeclaration::class)
    }
}