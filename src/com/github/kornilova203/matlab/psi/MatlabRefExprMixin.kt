package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.MatlabReference
import com.github.kornilova203.matlab.psi.types.*
import com.intellij.lang.ASTNode

/**
 * @author Liudmila Kornilova
 **/
abstract class MatlabRefExprMixin(node: ASTNode) : MatlabASTWrapperPsiElement(node), MatlabRefExpr, MatlabTypedExpr {
    override fun getReference(): MatlabReference = MatlabReference(this)

    override fun getName(): String? = text

    override fun getType(): MatlabType {
        val assign = this.isLeftPartOfAssign()
        if (assign != null && !this.isLeftPartQualified()) {
            return if (assign is MatlabTypedExpr) assign.getType() else MatlabTypeUnknown()
        }

        return when(val declaration = this.reference.resolve()) {
            null -> {
                when(this.text) {
                    "true", "false" -> MatlabTypeBool()
                    "struct" -> MatlabTypeStruct()
                    else -> MatlabTypeUnknown()
                }
            }
            !is MatlabTypedExpr -> MatlabTypeUnknown()
            else -> declaration.getType()
        }
    }
}
