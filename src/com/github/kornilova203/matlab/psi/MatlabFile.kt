package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.MatlabFileType
import com.github.kornilova203.matlab.MatlabLanguage
import com.github.kornilova203.matlab.processDeclarations
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor


class MatlabFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, MatlabLanguage.INSTANCE) {
    override fun getFileType(): FileType = MatlabFileType

    override fun toString(): String = "Matlab File"

    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        processDeclarations(this, processor, state, lastParent, place)
        return false
    }
}