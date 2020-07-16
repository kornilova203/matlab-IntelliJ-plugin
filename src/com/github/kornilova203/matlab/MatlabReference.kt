package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.MatlabDeclaration
import com.github.kornilova203.matlab.psi.MatlabRefExpr
import com.github.kornilova203.matlab.psi.MatlabResolvingScopeProcessor
import com.github.kornilova203.matlab.psi.MatlabTypes
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.util.KeyWithDefaultValue
import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType

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
        if (isResolved(processor)) {
            return processor.declaration
        }
        val psiManager = PsiManager.getInstance(element.project)
        ProjectRootManager.getInstance(element.project).fileIndex.iterateContent { file ->
            if (!file.isDirectory) {
                val psiFile = psiManager.findFile(file)
                if (psiFile != null && psiFile != containingFile) {
                    PsiTreeUtil.treeWalkUp(processor, psiFile.lastChild, psiFile, ResolveState.initial())
                    if (isResolved(processor)) {
                        return@iterateContent false
                    }
                }
            }
            return@iterateContent true
        }
        return if (processor.declaration == myElement) null else processor.declaration
    }

    private fun isResolved(processor: MatlabResolvingScopeProcessor): Boolean {
        return processor.declaration != null && processor.declaration != myElement
    }

    override fun getVariants(): Array<PsiElement> = emptyArray()
}
