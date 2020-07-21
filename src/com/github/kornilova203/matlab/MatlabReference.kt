package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.*
import com.github.kornilova203.matlab.stub.MatlabClassDeclarationIndex
import com.github.kornilova203.matlab.stub.MatlabFunctionDeclarationIndex
import com.github.kornilova203.matlab.stub.MatlabGlobalVariableIndex
import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.stubs.StubIndexKey
import com.intellij.psi.util.PsiTreeUtil

/**
 * @author Liudmila Kornilova
 **/
class MatlabReference(myElement: MatlabRefExpr) : PsiPolyVariantReferenceBase<MatlabRefExpr>(myElement) {
    companion object {
        private const val USE_CACHE = true
        private val RESOLVER = ResolveCache.AbstractResolver { ref: MatlabReference, _: Boolean -> ref.resolveInner() }
    }

    override fun resolve(): MatlabDeclaration? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element as MatlabDeclaration else null
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return if (USE_CACHE) {
            val cache = ResolveCache.getInstance(element.project)
            cache.resolveWithCaching(this, RESOLVER, false, false)!!
        } else {
            resolveInner()
        }
    }

    private fun resolveInner(): Array<ResolveResult>{
        val res = mutableListOf<ResolveResult>()
        val processor = MatlabResolvingScopeProcessor(this)
        val containingFile = myElement.containingFile
        PsiTreeUtil.treeWalkUp(processor, myElement, containingFile, ResolveState.initial())
        if (isResolved(processor)) {
            return arrayOf(PsiElementResolveResult(processor.declaration!!))
        }
        resolveIndex(processor, MatlabFunctionDeclarationIndex.KEY, MatlabFunctionDeclaration::class.java)
        resolveIndex(processor, MatlabClassDeclarationIndex.KEY, MatlabClassDeclaration::class.java)
        if (processor.declaration !is MatlabGlobalVariableDeclaration) {
            return if (isResolved(processor)) arrayOf(PsiElementResolveResult(processor.declaration!!)) else emptyArray()
        }
        StubIndex.getInstance().processElements(MatlabGlobalVariableIndex.KEY, myElement.text, myElement.project, GlobalSearchScope.projectScope(myElement.project), MatlabGlobalVariableDeclaration::class.java) { psiElement ->
            res.add(PsiElementResolveResult(psiElement))
            return@processElements true
        }
        return res.toTypedArray()
    }

    private fun isResolved(processor: MatlabResolvingScopeProcessor): Boolean {
        val declaration = processor.declaration
        return declaration != null && declaration != myElement && declaration !is MatlabGlobalVariableDeclaration
    }

    private fun <Psi : PsiElement?> resolveIndex(processor: PsiScopeProcessor, indexKey: StubIndexKey<String, Psi>, requiredClass : Class<Psi>) {
        StubIndex.getInstance().processElements(indexKey, myElement.text, myElement.project, GlobalSearchScope.projectScope(myElement.project), requiredClass) { psiElement ->
            if (psiElement != null) {
                if (psiElement.containingFile != myElement.containingFile && psiElement.containingFile.name == myElement.text + ".m") {
                    processor.execute(psiElement, ResolveState.initial())
                }
            }
            return@processElements true
        }
    }

    override fun getVariants(): Array<PsiElement> = emptyArray()
}
