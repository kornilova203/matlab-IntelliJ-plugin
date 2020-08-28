package com.github.kornilova203.matlab.psi.types

import com.github.kornilova203.matlab.MatlabReference
import com.github.kornilova203.matlab.psi.*
import com.github.kornilova203.matlab.psi.MatlabTypes.*
import com.intellij.psi.util.elementType
import com.intellij.psi.util.parentOfTypes

fun getTypeFromDeclaration(declaration: MatlabDeclaration?): MatlabType {
    return when (declaration) {
        is MatlabClassDeclaration -> MatlabTypeClass(declaration)
        is MatlabFunctionDeclaration -> {
            val cl = declaration.isConstructor()
            if (cl != null) MatlabTypeClass(cl) else MatlabTypeFunction(declaration)
        }
        is MatlabAssignExpr -> getTypeFromExpr(declaration.right)
        else -> MatlabTypeUnknown()
    }
}

fun getTypeFromExpr(expr: MatlabExpr?): MatlabType {
    return when (expr) {
        is MatlabRefExpr -> (expr.reference as MatlabReference).getType()
        is MatlabLiteralExpr -> {
            val child = expr.firstChild
            when (child.elementType) {
                INTEGER, FLOAT, FLOAT_EXPONENTIAL -> MatlabTypeScalar()
                DOUBLE_QUOTE_STRING, SINGLE_QUOTE_STRING -> MatlabTypeString()
                CELL_ARRAY_LITERAL -> MatlabTypeCell()
                MATRIX_LITERAL -> MatlabTypeMatrix()
                else -> MatlabTypeUnknown()
            }
        }
        is MatlabFunctionExpr -> getTypeFromExpr(expr.expr)
        is MatlabBinaryExpr -> getTypeFromBinaryExpr(expr)
        else -> MatlabTypeUnknown()
    }
}

fun getTypeFromRefExpr(ref: MatlabRefExpr): MatlabType {
    val assign = ref.isLeftPartOfAssign()
    if (assign != null && !ref.isLeftPartQualified()) {
        return getTypeFromDeclaration(assign as MatlabDeclaration)
    }

    val declaration = ref.reference?.resolve()

    if (declaration == null) {
        if (ref.text == "true" || ref.text == "false") {
            return MatlabTypeBool()
        }
        if (ref.text == "struct") {
            return MatlabTypeStruct()
        }
        return MatlabTypeUnknown()
    }
    if (ref.parent is MatlabFunctionExpr && declaration !is MatlabClassDeclaration) {
        return MatlabTypeUnknown()
    }
    return getTypeFromDeclaration(declaration as MatlabDeclaration)
}

fun getTypeFromBinaryExpr(expr: MatlabBinaryExpr): MatlabType {
    val left = getTypeFromExpr(expr.left)
    val right = getTypeFromExpr(expr.right)
    return when (expr) {
        is MatlabAndExpr, is MatlabOrExpr, is MatlabMatrixAndExpr, is MatlabMatrixOrExpr, is MatlabUnaryNegationExpr, is MatlabEqualExpr,
        is MatlabNotEqualExpr, is MatlabLessExpr, is MatlabLessOrEqualExpr, is MatlabMoreExpr, is MatlabMoreOrEqualExpr -> MatlabTypeBool()
        is MatlabPlusExpr, is MatlabMinusExpr, is MatlabMulExpr, is MatlabRdivExpr, is MatlabLdivExpr, is MatlabPowExpr,
        is MatlabElementWiseMulExpr, is MatlabElementWiseLdivExpr, is MatlabElementWiseRdivExpr, is MatlabElementWisePowExpr -> {
            when {
                left is MatlabTypeScalar && right is MatlabTypeScalar -> MatlabTypeScalar()
                left is MatlabTypeMatrix && right is MatlabTypeMatrix -> MatlabTypeMatrix()
                left is MatlabTypeMatrix && right is MatlabTypeScalar || right is MatlabTypeMatrix && left is MatlabTypeScalar -> MatlabTypeMatrix()
                else -> MatlabTypeUnknown()
            }
        }
        is MatlabUnaryPlusExpr, is MatlabUnaryMinExpr -> {
            when (left) {
                is MatlabTypeScalar -> MatlabTypeScalar()
                is MatlabTypeMatrix -> MatlabTypeMatrix()
                else -> MatlabTypeUnknown()
            }
        }
        else -> MatlabTypeUnknown()
    }
}

fun MatlabFunctionDeclaration.isConstructor(): MatlabClassDeclaration? {
    val classDeclaration = this.parentOfTypes(MatlabClassDeclaration::class) ?: return null
    val className = classDeclaration.getChildOfType(IDENTIFIER)?.text ?: return null
    val functionName = this.getChildOfType(IDENTIFIER)?.text ?: return null
    return if (className == functionName) classDeclaration else null
}