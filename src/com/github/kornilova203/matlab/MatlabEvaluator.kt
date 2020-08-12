package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.util.elementType

fun evaluateAsBoolean(expr: MatlabExpr?): Boolean? {
    return when (val value = evaluateExpr(expr)) {
        is Boolean -> return value
        is Double -> return value != 0.0 && value != -0.0
        is String -> return value != ""
        else -> null
    }
}

fun evaluateExpr(expr: MatlabExpr?): Any? {
    return when (expr) {
        is MatlabLiteralExpr -> evaluateLiteral(expr)
        is MatlabBinaryExpr -> evaluateBinaryExpr(expr)
        is MatlabParenExpr -> evaluateExpr(expr.expr)
        is MatlabRefExpr -> evaluateRefExpr(expr)
        is MatlabFunctionExpr -> evaluateRefExpr(expr.expr as MatlabRefExpr)
        else -> null
    }
}

fun evaluateLiteral(expr: MatlabLiteralExpr?): Any? {
    val child = expr?.firstChild
    val text = child?.text
    return when(child.elementType) {
        MatlabTypes.INTEGER, MatlabTypes.FLOAT, MatlabTypes.FLOAT_EXPONENTIAL -> text?.toDouble()
        MatlabTypes.SINGLE_QUOTE_STRING, MatlabTypes.DOUBLE_QUOTE_STRING -> text?.substring(1, text.length - 1)
        else -> null
    }
}

fun evaluateBinaryExpr(expr: MatlabBinaryExpr): Any? {
    val left = evaluateExpr(expr.left)
    val right = evaluateExpr(expr.right)
    if (left is Double && right == null) {
        return when (expr) {
            is MatlabUnaryPlusExpr -> left
            is MatlabUnaryMinExpr -> -left
            is MatlabUnaryNegationExpr -> left == 0.0
            else -> null
        }
    }
    if (left is Double && right is Double) {
        return when (expr) {
            is MatlabPlusExpr -> left + right
            is MatlabMinusExpr -> left - right
            is MatlabMulExpr -> left * right
            is MatlabRdivExpr -> left / right
            is MatlabLdivExpr -> right / left
            is MatlabPowExpr -> Math.pow(left, right)
            is MatlabEqualExpr -> left == right
            is MatlabNotEqualExpr -> left != right
            is MatlabLessExpr -> left < right
            is MatlabLessOrEqualExpr -> left <= right
            is MatlabMoreExpr -> left > right
            is MatlabMoreOrEqualExpr -> left >= right
            is MatlabAndExpr -> left != 0.0 && right != 0.0
            is MatlabOrExpr -> left != 0.0 || right != 0.0
            is MatlabMatrixAndExpr -> left != 0.0 && right != 0.0
            is MatlabMatrixOrExpr -> left != 0.0 || right != 0.0
            else -> null
        }
    }
    if (left is Boolean && right == null) {
        return if (expr is MatlabUnaryNegationExpr) !left else null
    }
    if (left is Boolean && right is Boolean) {
        return when (expr) {
            is MatlabAndExpr -> left && right
            is MatlabOrExpr -> left || right
            is MatlabMatrixAndExpr -> left && right
            is MatlabMatrixOrExpr -> left || right
            else -> null
        }
    }
    return null
}

fun evaluateRefExpr(expr: MatlabRefExpr?): Any? {
    if (expr != null && (expr.text == "true" || expr.text == "false")) {
        val scope = GlobalSearchScope.fileScope(expr.containingFile)
        val refs = ReferencesSearch.search(expr, scope).findAll()
        if (refs.isEmpty()) {
            if (expr.text == "true") return true
            if (expr.text == "false") return false
        }
    }
    return null
}