package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.psi.types.*
import com.intellij.lang.ASTNode

abstract class MatlabBinaryExprMixin(node: ASTNode) : MatlabASTWrapperPsiElement(node), MatlabBinaryExpr, MatlabTypedExpr {
    override fun getType(): MatlabType {
        val leftType = if (left is MatlabTypedExpr) (left as MatlabTypedExpr).getType() else MatlabTypeUnknown()
        val rightType = if (right is MatlabTypedExpr) (right as MatlabTypedExpr).getType() else MatlabTypeUnknown()
        return when (this) {
            is MatlabAndExpr, is MatlabOrExpr, is MatlabMatrixAndExpr, is MatlabMatrixOrExpr, is MatlabUnaryNegationExpr, is MatlabEqualExpr,
            is MatlabNotEqualExpr, is MatlabLessExpr, is MatlabLessOrEqualExpr, is MatlabMoreExpr, is MatlabMoreOrEqualExpr -> MatlabTypeBool()
            is MatlabPlusExpr, is MatlabMinusExpr, is MatlabMulExpr, is MatlabRdivExpr, is MatlabLdivExpr, is MatlabPowExpr,
            is MatlabElementWiseMulExpr, is MatlabElementWiseLdivExpr, is MatlabElementWiseRdivExpr, is MatlabElementWisePowExpr -> {
                when {
                    leftType is MatlabTypeScalar && rightType is MatlabTypeScalar -> MatlabTypeScalar()
                    leftType is MatlabTypeMatrix && rightType is MatlabTypeMatrix -> MatlabTypeMatrix()
                    leftType is MatlabTypeMatrix && rightType is MatlabTypeScalar || rightType is MatlabTypeMatrix && leftType is MatlabTypeScalar -> MatlabTypeMatrix()
                    else -> MatlabTypeUnknown()
                }
            }
            is MatlabUnaryPlusExpr, is MatlabUnaryMinExpr -> {
                when (leftType) {
                    is MatlabTypeScalar -> MatlabTypeScalar()
                    is MatlabTypeMatrix -> MatlabTypeMatrix()
                    else -> MatlabTypeUnknown()
                }
            }
            else -> MatlabTypeUnknown()
        }
    }
}