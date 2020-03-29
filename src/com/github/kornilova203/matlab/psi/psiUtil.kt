package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.MatlabLanguage
import com.github.kornilova203.matlab.psi.MatlabTypes.IDENTIFIER
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.tree.IElementType

private fun createFile(project: Project, text: String): PsiFile {
    return PsiFileFactory.getInstance(project).createFileFromText("a.m", MatlabLanguage.INSTANCE, text, false, false)
}

private fun createRefFromText(project: Project, text: String): MatlabRefExprMixin {
    return createFile(project, text).firstChild as MatlabRefExprMixin
}

fun createIdentifierFromText(project: Project, text: String): PsiElement {
    return createRefFromText(project, text).identifier()
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

