package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.MatlabLanguage
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil

/**
 * @author Liudmila Kornilova
 **/
object MatlabPsiUtil {

    private fun createFile(project: Project, text: String): PsiFile {
        return PsiFileFactory.getInstance(project).createFileFromText("a.m", MatlabLanguage.INSTANCE, text, false, false)
    }

    private fun createRefFromText(project: Project, text: String): MatlabRefExprMixin {
        return createFile(project, text).firstChild as MatlabRefExprMixin
    }

    fun createIdentifierFromText(project: Project, text: String): PsiElement {
        return createRefFromText(project, text).identifier
    }

    fun getChildOfType(e: PsiElement?, type: IElementType?): PsiElement? {
        if (e == null || type == null) return null
        val child = e.firstChild
        return if (child == null) null else PsiTreeUtil.findSiblingForward(child, type, false, null)
    }
}