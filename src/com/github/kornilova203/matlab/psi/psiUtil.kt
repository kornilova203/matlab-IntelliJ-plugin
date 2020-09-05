package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.MatlabLanguage
import com.github.kornilova203.matlab.psi.MatlabTypes.IDENTIFIER
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.psi.util.parentOfTypes
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import com.intellij.psi.util.parentOfTypes

private fun createFile(project: Project, text: String): PsiFile {
    return PsiFileFactory.getInstance(project).createFileFromText("a.m", MatlabLanguage.INSTANCE, text, false, false)
}

private fun createRefFromText(project: Project, text: String): MatlabRefExprMixin {
    return createFile(project, text).firstChild as MatlabRefExprMixin
}

fun createIdentifierFromText(project: Project, text: String): PsiElement {
    return createRefFromText(project, text).identifier()
}

fun createElementFromText(project: Project, text: String): PsiElement {
    return createFile(project, text).firstChild
}

fun MatlabRefExpr.identifier(): PsiElement {
    return getChildOfType(IDENTIFIER)!!
}

fun PsiElement.getChildOfType(type: IElementType?): PsiElement? {
    if (type == null) return null
    val child = firstChild ?: return null
    return child.findSiblingForward(type)
}

private fun PsiElement.findSiblingForward(type: IElementType): PsiElement? {
    // PsiTreeUtil.findSiblingForward is available since 173
    var e: PsiElement? = this
    while (e != null) {
        if (type == e.node.elementType) return e
        e = e.nextSibling
    }
    return null
}

fun MatlabRefExpr.isLeftPartOfAssign() : MatlabAssignExpr? {
    val assign = this.parentOfTypes(MatlabAssignExpr::class) ?: return null
    return if (PsiTreeUtil.isAncestor(assign.left, this, false)) assign else null
}

fun MatlabRefExpr.isLeftPartQualified(): Boolean {
    val parent = this.parent
    return parent is MatlabQualifiedExpr && this == parent.left
}

fun MatlabFunctionDeclaration.isConstructor(): MatlabClassDeclaration? {
    val classDeclaration = this.parentOfTypes(MatlabClassDeclaration::class) ?: return null
    val className = classDeclaration.getChildOfType(IDENTIFIER)?.text ?: return null
    val functionName = this.getChildOfType(IDENTIFIER)?.text ?: return null
    return if (className == functionName) classDeclaration else null
}

fun deleteExpr(expr: PsiElement) {
    trim(expr)
    if (expr.nextSibling.elementType == MatlabTypes.SEMICOLON) {
        expr.nextSibling.delete()
        deleteWhiteSpace(expr.nextSibling)
    }
    when {
        expr.nextSibling.elementType == MatlabTypes.NEWLINE -> expr.nextSibling
        expr.prevSibling.elementType == MatlabTypes.NEWLINE -> expr.prevSibling
        else -> null
    }?.delete()
    expr.delete()
}

fun deleteElementInList(element: PsiElement, separator: IElementType) {
    deleteWhiteSpace(element.nextSibling)
    if (element.nextSibling.elementType == separator) {
        element.nextSibling.delete()
        deleteWhiteSpace(element.nextSibling)
    } else {
        deleteWhiteSpace(element.prevSibling)
        if (element.prevSibling.elementType == separator) {
            element.prevSibling.delete()
        }
    }
    element.delete()
}

fun trim(element: PsiElement) {
    val file = element.containingFile
    deleteWhiteSpace(file.findElementAt(element.startOffset - 1))
    deleteWhiteSpace(file.findElementAt(element.endOffset))
}

fun deleteWhiteSpace(element: PsiElement?) {
    if (element != null && element is PsiWhiteSpace) {
        element.delete()
    }
}
fun findClassDeclaration(element: PsiElement?): MatlabClassDeclaration? {
    val ref = getRefExpr(element) ?: return null
    val declaration = ref.reference?.resolve()
    return when (declaration) {
        is MatlabClassDeclaration -> declaration
        is MatlabAssignExpr -> findClassDeclaration(declaration.right)
        is MatlabFunctionDeclaration -> isMethod(declaration)
        is MatlabProperty -> findClassDeclaration(declaration.className?.refExpr)
        is MatlabRetValue -> isMethod(declaration.parentOfTypes(MatlabFunctionDeclaration::class) ?: return null)
        is MatlabParameter -> isFirstParameter(declaration)
        else -> null
    }
}

fun getRefExpr(element: PsiElement?): PsiElement? {
    return when (element) {
        is MatlabRefExpr -> element
        is MatlabFunctionExpr -> element.getChildOfType(MatlabTypes.REF_EXPR)
        is MatlabQualifiedExpr -> getRefExpr(element.right)
        else -> null
    }
}

fun isMethod(function: MatlabFunctionDeclaration): MatlabClassDeclaration? {
    return isConstructor(function) ?: isClassReturn(function)
}

private fun isConstructor(function: MatlabFunctionDeclaration): MatlabClassDeclaration? {
    val classDeclaration = function.parentOfTypes(MatlabClassDeclaration::class) ?: return null
    val className = classDeclaration.getChildOfType(IDENTIFIER)?.text ?: return null
    val functionName = function.getChildOfType(IDENTIFIER)?.text ?: return null
    return if (className == functionName) classDeclaration else null
}

private fun isClassReturn(function: MatlabFunctionDeclaration): MatlabClassDeclaration? {
    val classDeclaration = function.parentOfTypes(MatlabClassDeclaration::class) ?: return null
    val retValues = function.returnValues?.retValueList
    if (retValues?.size == 1) {
        val retValue = retValues[0].text
        val parameterList = function.parameters?.parameterList
        val leftParameter = parameterList?.get(0)?.text
        return if (retValue == leftParameter) classDeclaration else null
    }
    return null
}

fun isFirstParameter(parameter: MatlabParameter): MatlabClassDeclaration? {
    val parametersList = parameter.parentOfTypes(MatlabParametersList::class) ?: return null
    val list = parametersList.parameterList
    if (list.size == 0 || list[0] != parameter) {
        return null
    }
    return parameter.parentOfTypes(MatlabClassDeclaration::class)
}

