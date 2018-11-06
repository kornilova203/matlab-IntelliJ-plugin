package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.psi.impl.MatlabFunctionDeclarationImpl
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabFunctionDeclarationMixin(node: ASTNode) : ASTWrapperPsiElement(node), MatlabDeclaration, MatlabFunctionDeclaration {
    override fun getIdentifier(): MatlabRef? = (this as? MatlabFunctionDeclarationImpl)?.ref

    private val returnValuesRefList: List<MatlabRef> by lazy {
        returnValues?.let { return@lazy it.refList }
        return@lazy emptyList<MatlabRef>()
    }

    private val parametersRefList: List<MatlabRef> by lazy {
        parameters?.let { return@lazy it.parameterList.map { parameter -> parameter.ref } }
        return@lazy emptyList<MatlabRef>()
    }

    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        for (matlabRef in returnValuesRefList) {
            if (!processor.execute(matlabRef, state)) return false
        }
        for (matlabRef in parametersRefList) {
            if (!processor.execute(matlabRef, state)) return false
        }
        return false
    }

    override val implicit: Boolean = false
}
