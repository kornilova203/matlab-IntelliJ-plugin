package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.psi.types.*
import com.intellij.lang.ASTNode
import com.intellij.psi.util.elementType

abstract class MatlabLiteralExprMixin(node: ASTNode) : MatlabASTWrapperPsiElement(node), MatlabLiteralExpr, MatlabTypedExpr  {
    override fun getType(): MatlabType {
        val child = this.firstChild
        return when (child.elementType) {
            MatlabTypes.INTEGER, MatlabTypes.FLOAT, MatlabTypes.FLOAT_EXPONENTIAL -> MatlabTypeScalar()
            MatlabTypes.DOUBLE_QUOTE_STRING, MatlabTypes.SINGLE_QUOTE_STRING -> MatlabTypeString()
            MatlabTypes.CELL_ARRAY_LITERAL -> MatlabTypeCell()
            MatlabTypes.MATRIX_LITERAL -> MatlabTypeMatrix()
            else -> MatlabTypeUnknown()
        }
    }
}