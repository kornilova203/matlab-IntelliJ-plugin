package com.github.korniloval.matlab.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.impl.source.resolve.reference.impl.CachingReference
import com.intellij.psi.util.PsiTreeUtil

/**
 * @author Liudmila Kornilova
 **/
class MatlabReference(val myElement: PsiElement) : CachingReference() {

    override fun getElement(): PsiElement = myElement

    override fun resolveInner(): PsiElement? {
        val processor = MatlabResolvingScopeProcessor(this)
        val state = ResolveState.initial()

        val containingFile = myElement.containingFile

        PsiTreeUtil.treeWalkUp(processor, myElement, containingFile, state)
        return if (processor.declaration == myElement) null else processor.declaration
    }

    override fun getVariants(): Array<Any> = emptyArray()

    override fun getRangeInElement(): TextRange = TextRange.create(0, myElement.textRange.endOffset - myElement.textRange.startOffset)

    override fun getCanonicalText(): String = myElement.text

    override fun handleElementRename(newElementName: String): PsiElement {
        TODO("not implemented")
    }

    override fun bindToElement(element: PsiElement): PsiElement {
        TODO("not implemented")
    }
}
