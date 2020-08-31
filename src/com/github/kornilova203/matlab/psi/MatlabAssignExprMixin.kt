package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.psi.impl.MatlabBinaryExprImpl
import com.github.kornilova203.matlab.psi.types.MatlabType
import com.github.kornilova203.matlab.psi.types.MatlabTypeUnknown
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.tree.IElementType

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabAssignExprMixin(node: ASTNode) : MatlabBinaryExprImpl(node), MatlabDeclaration, MatlabAssignExpr, MatlabTypedExpr {

    override val visibleOutsideFunction = false

    override fun getNameIdentifier(): PsiElement? {
        (left as? MatlabRefExpr)?.let { return it.identifier() }
        return null
    }

    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement {
        nameIdentifier?.replace(createIdentifierFromText(project, name))
        return this
    }

    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        if (place == left) processor.execute(this, state)
        return true
    }

    override fun getType(): MatlabType {
        val expr = this.right
        return if (expr is MatlabTypedExpr) expr.getType() else MatlabTypeUnknown()
    }
}
