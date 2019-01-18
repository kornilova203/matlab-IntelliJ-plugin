package com.github.korniloval.matlab.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabLambdaExprMixin(elementType: IElementType) : MatlabCompositePsiElement(elementType), MatlabLambdaExpr {

    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        parameters.parameterList.forEach { parameter ->
            val matlabRef = parameter.ref
            if (matlabRef != null) {
                if (!processor.execute(matlabRef, state)) return false
            }
        }
        return false
    }
}
