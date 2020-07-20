package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.*
import com.github.kornilova203.matlab.stub.MatlabClassDeclarationIndex
import com.github.kornilova203.matlab.stub.MatlabFunctionDeclarationIndex
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveState
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.stubs.StubIndexKey
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
        if (isResolved(processor)) {
            return processor.declaration
        }
        resolveIndex(processor, MatlabFunctionDeclarationIndex.KEY, MatlabFunctionDeclaration::class.java)
        resolveIndex(processor, MatlabClassDeclarationIndex.KEY, MatlabClassDeclaration::class.java)
        return if (processor.declaration == myElement) null else processor.declaration
    }

    private fun isResolved(processor: MatlabResolvingScopeProcessor): Boolean {
        return processor.declaration != null && processor.declaration != myElement
    }

    private fun <Psi : PsiElement?> resolveIndex(processor: PsiScopeProcessor, indexKey: StubIndexKey<String, Psi>, requiredClass : Class<Psi>) {
        val psiElements = StubIndex.getElements(indexKey, myElement.text, myElement.project, GlobalSearchScope.projectScope(myElement.project), requiredClass)
        for (psiElement in psiElements) {
            if (psiElement != null) {
                if (psiElement.containingFile != myElement.containingFile) {
                    processor.execute(psiElement, ResolveState.initial())
                }
            }
        }
    }

    override fun getVariants(): Array<PsiElement> = emptyArray()
}
