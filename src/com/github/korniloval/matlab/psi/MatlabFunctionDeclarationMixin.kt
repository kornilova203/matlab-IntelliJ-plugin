package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.psi.impl.MatlabFunctionDeclarationImpl
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabFunctionDeclarationMixin(elementType: IElementType) : MatlabCompositePsiElement(elementType), MatlabDeclaration, MatlabFunctionDeclaration {
    override fun getIdentifier(): MatlabRef? = (this as? MatlabFunctionDeclarationImpl)?.ref

    private fun returnValuesRefList(): List<MatlabRef> {
        returnValues?.let { return it.refList }
        return emptyList()
    }

    private fun parametersRefList(): List<MatlabRef> {
        parameters?.let { return it.parameterList.map { parameter -> parameter.ref } }
        return emptyList()
    }

    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        for (matlabRef in returnValuesRefList()) {
            if (!processor.execute(matlabRef, state)) return false
        }
        for (matlabRef in parametersRefList()) {
            if (!processor.execute(matlabRef, state)) return false
        }
        return false
    }

    override val implicit: Boolean = false
}
