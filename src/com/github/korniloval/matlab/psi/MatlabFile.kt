package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.MatlabFileType
import com.github.korniloval.matlab.MatlabLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

fun processChildren(parent: PsiElement, consumer: (PsiElement) -> Unit) {
    var child = parent.firstChild
    while (child != null) {
        consumer(child)
        child = child.nextSibling
    }
}

fun processDeclarations(parent: PsiElement, processor: PsiScopeProcessor, state: ResolveState): Boolean {
    var shouldContinue = true
    processChildren(parent) { child ->
        if (child is MatlabDeclaration) {
            if (!processor.execute(child, state)) {
                shouldContinue = false
                return@processChildren
            }
        }
    }
    return shouldContinue
}

class MatlabFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, MatlabLanguage.INSTANCE) {
    override fun getFileType(): FileType = MatlabFileType.INSTANCE

    override fun toString(): String = "Matlab File"

    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        processDeclarations(this, processor, state)
        return false
    }
}