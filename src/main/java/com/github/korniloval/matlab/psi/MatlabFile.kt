package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.MatlabFileType
import com.github.korniloval.matlab.MatlabLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class MatlabFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, MatlabLanguage.INSTANCE) {
    override fun getFileType(): FileType = MatlabFileType.INSTANCE

    override fun toString(): String = "Matlab File"
}