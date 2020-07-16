package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.*
import com.github.kornilova203.matlab.stub.MatlabClassDeclarationIndex
import com.github.kornilova203.matlab.stub.MatlabFunctionDeclarationIndex
import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
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
        val project = myElement.project
        PsiTreeUtil.treeWalkUp(processor, myElement, containingFile, ResolveState.initial())
        if (isResolved(processor)) {
            return processor.declaration
        }
        val functionDeclarations = StubIndex.getElements(MatlabFunctionDeclarationIndex.KEY, myElement.text, project, GlobalSearchScope.projectScope(project), MatlabFunctionDeclaration::class.java)
        for (functionDeclaration in functionDeclarations) {
            if (functionDeclaration.containingFile != containingFile) {
                processor.execute(functionDeclaration, ResolveState.initial())
            }
        }
        val classDeclarations = StubIndex.getElements(MatlabClassDeclarationIndex.KEY, myElement.text, project, GlobalSearchScope.projectScope(project), MatlabClassDeclaration::class.java)
        for (classDeclaration in classDeclarations) {
            if (classDeclaration.containingFile != containingFile) {
                processor.execute(classDeclaration, ResolveState.initial())
            }
        }
        return if (processor.declaration == myElement) null else processor.declaration
    }

    private fun isResolved(processor: MatlabResolvingScopeProcessor): Boolean {
        return processor.declaration != null && processor.declaration != myElement
    }

    override fun getVariants(): Array<PsiElement> = emptyArray()
}
