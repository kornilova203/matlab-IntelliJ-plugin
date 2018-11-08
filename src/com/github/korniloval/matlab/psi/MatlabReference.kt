package com.github.korniloval.matlab.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveState
import com.intellij.psi.util.PsiTreeUtil

/**
 * @author Liudmila Kornilova
 **/
class MatlabReference(myElement: MatlabRef) : PsiReferenceBase<MatlabRef>(myElement) {

    override fun resolve(): MatlabRef? = resolveInner()

    private fun resolveInner(): MatlabRef? {
        val processor = MatlabResolvingScopeProcessor(this)
        val containingFile = myElement.containingFile
        PsiTreeUtil.treeWalkUp(processor, myElement, containingFile, ResolveState.initial())
        return if (processor.declaration == myElement) null else processor.declaration
    }

    override fun getVariants(): Array<PsiElement> = emptyArray()
}
