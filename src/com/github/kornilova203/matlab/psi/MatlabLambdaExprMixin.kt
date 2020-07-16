package com.github.kornilova203.matlab.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabLambdaExprMixin(node: ASTNode) : MatlabASTWrapperPsiElement(node), MatlabLambdaExpr {

    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        parameters.parameterList.forEach { parameter ->
            if (!processor.execute(parameter, state)) return false
        }
        return true
    }
}
