package com.github.korniloval.matlab

import com.github.korniloval.matlab.psi.MatlabDeclaration
import com.github.korniloval.matlab.psi.MatlabRefExpr
import com.github.korniloval.matlab.psi.MatlabResolvingScopeProcessor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveState
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.util.PsiTreeUtil

/**
 * @author Liudmila Kornilova
 **/
class MatlabReference(myElement: MatlabRefExpr) : PsiReferenceBase<MatlabRefExpr>(myElement) {
    companion object {
        private const val USE_CACHE = true
        private val RESOLVER = ResolveCache.AbstractResolver { ref: MatlabReference, _: Boolean -> ref.resolveInner() }
    }

    override fun resolve(): MatlabDeclaration? {
        return if (USE_CACHE) {
            val cache = ResolveCache.getInstance(element.project)
            cache.resolveWithCaching(this, RESOLVER, false, false)
        } else {
            resolveInner()
        }
    }

    private fun resolveInner(): MatlabDeclaration? {
        val processor = MatlabResolvingScopeProcessor(this)
        val containingFile = myElement.containingFile
        PsiTreeUtil.treeWalkUp(processor, myElement, containingFile, ResolveState.initial())
        return if (processor.declaration == myElement) null else processor.declaration
    }

    override fun getVariants(): Array<PsiElement> = emptyArray()
}
