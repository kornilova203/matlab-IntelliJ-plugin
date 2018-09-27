package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.MatlabFileType
import com.github.korniloval.matlab.MatlabLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import java.util.function.Consumer

fun processChildren(parent: PsiElement, consumer: Consumer<PsiElement>) {
    var child = parent.firstChild
    while (child != null) {
        consumer.accept(child)
        child = child.nextSibling
    }
}

fun processDeclarations(parent: PsiElement, processor: PsiScopeProcessor, state: ResolveState): Boolean {
    var shouldContinue = true
    processChildren(parent, Consumer { child ->
        if (shouldContinue && child is MatlabDeclaration) {
            val identifier = child.getIdentifier()
            if (identifier != null) {
                shouldContinue = processor.execute(identifier, state)
            }
        }
    })
    return shouldContinue
}

class MatlabFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, MatlabLanguage.INSTANCE) {
    override fun getFileType(): FileType = MatlabFileType.INSTANCE

    override fun toString(): String = "Matlab File"

    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        return processDeclarations(this, processor, state)
    }
}