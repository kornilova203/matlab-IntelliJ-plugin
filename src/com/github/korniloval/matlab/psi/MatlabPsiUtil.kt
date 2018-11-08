package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.MatlabLanguage
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory

/**
 * @author Liudmila Kornilova
 **/
object MatlabPsiUtil {

    private fun createFile(project: Project, text: String): PsiFile {
        return PsiFileFactory.getInstance(project).createFileFromText("a.m", MatlabLanguage.INSTANCE, text, false, false)
    }

    fun createRefFromText(project: Project, text: String): MatlabRefMixin {
        val firstChild = createFile(project, text).firstChild as MatlabRefExpr
        return firstChild.ref as MatlabRefMixin
    }
}