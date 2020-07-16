package com.github.kornilova203.matlab.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabFunctionDeclarationMixin(node: ASTNode) : MatlabDeclarationBase(node), MatlabFunctionDeclaration {

    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        returnValues?.retValueList?.forEach { retValue ->
            if (!processor.execute(retValue, state)) return false
        }

        parameters?.parameterList?.forEach { parameter ->
            if (!processor.execute(parameter, state)) return false
        }
        return true
    }
}
