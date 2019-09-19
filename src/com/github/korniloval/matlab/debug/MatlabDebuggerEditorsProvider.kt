package com.github.korniloval.matlab.debug

import com.github.korniloval.matlab.MatlabFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProviderBase

class MatlabDebuggerEditorsProvider : XDebuggerEditorsProviderBase() {
    override fun createExpressionCodeFragment(project: Project, text: String, context: PsiElement?, isPhysical: Boolean): PsiFile {
        throw UnsupportedOperationException()
    }

    override fun getFileType() = MatlabFileType.INSTANCE
}
