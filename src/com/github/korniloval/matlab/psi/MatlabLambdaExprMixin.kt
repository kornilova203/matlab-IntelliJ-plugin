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
            if (!processor.execute(parameter, state)) return false
        }
        return true
    }
}
