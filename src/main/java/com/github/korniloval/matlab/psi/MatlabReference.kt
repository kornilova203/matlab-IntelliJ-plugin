package com.github.korniloval.matlab.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.ResolveState
import com.intellij.psi.util.PsiTreeUtil

/**
 * @author Liudmila Kornilova
 **/
class MatlabReference(val myElement: PsiElement, val implicit: Boolean) : PsiReference {

    override fun getElement(): PsiElement = myElement

    override fun resolve(): PsiElement? {
        val processor = MatlabResolvingScopeProcessor(this)
        val state = ResolveState.initial()

        val containingFile = myElement.containingFile

        PsiTreeUtil.treeWalkUp(processor, myElement, containingFile, state)
        return processor.declaration
    }

    override fun getVariants(): Array<Any> = emptyArray()

    override fun getRangeInElement(): TextRange = TextRange.create(0, myElement.textRange.endOffset - myElement.textRange.startOffset)

    override fun getCanonicalText(): String = myElement.text

    override fun handleElementRename(newElementName: String?): PsiElement {
        TODO("not implemented")
    }

    override fun bindToElement(element: PsiElement): PsiElement {
        TODO("not implemented")
    }

    override fun isSoft(): Boolean = false

    override fun isReferenceTo(element: PsiElement?): Boolean {
        TODO("not implemented")
    }
}
