package com.github.kornilova_l.matlab.psi

import com.github.kornilova_l.matlab.MatlabFileType
import com.github.kornilova_l.matlab.MatlabLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class MatlabFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, MatlabLanguage.INSTANCE) {
    override fun getFileType(): FileType = MatlabFileType.INSTANCE

    override fun toString(): String = "Matlab File"
}