package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.psi.impl.MatlabBinaryExprImpl
import com.github.kornilova203.matlab.psi.types.MatlabType
import com.github.kornilova203.matlab.psi.types.MatlabTypeClass
import com.github.kornilova203.matlab.psi.types.MatlabTypeUnknown
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.parentOfTypes

abstract class MatlabQualifiedExprMixin(node: ASTNode) : MatlabBinaryExprImpl(node), MatlabQualifiedExpr, MatlabTypedExpr {
    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        if (lastParent == this.right) {
            val element = this.firstChild
            if (element is MatlabTypedExpr) {
                val type = element.getType()
                if (type is MatlabTypeClass) {
                    val classDeclaration = type.decl
                    val declarations = PsiTreeUtil.findChildrenOfAnyType(classDeclaration, false,
                            MatlabProperty::class.java,
                            MatlabFunctionDeclaration::class.java,
                            MatlabEnumItem::class.java)

                    for (declaration in declarations) {
                        if (!processor.execute(declaration, state)) {
                            break
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    override fun getType(): MatlabType {
        val expr = lastChild
        return if (expr is MatlabTypedExpr) expr.getType() else MatlabTypeUnknown()
    }
}