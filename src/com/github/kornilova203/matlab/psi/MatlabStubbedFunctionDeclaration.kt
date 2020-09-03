package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.psi.types.MatlabType
import com.github.kornilova203.matlab.psi.types.MatlabTypeClass
import com.github.kornilova203.matlab.psi.types.MatlabTypeFunction
import com.github.kornilova203.matlab.stub.MatlabFunctionDeclarationStub
import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.StubBasedPsiElement
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.tree.IElementType

abstract class MatlabStubbedFunctionDeclaration : StubBasedPsiElementBase<MatlabFunctionDeclarationStub?>,
        StubBasedPsiElement<MatlabFunctionDeclarationStub>, MatlabDeclaration, MatlabFunctionDeclaration, MatlabTypedExpr {
    constructor(stub: MatlabFunctionDeclarationStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)
    constructor(node: ASTNode) : super(node)
    constructor(stub: MatlabFunctionDeclarationStub?, nodeType: IElementType?, node: ASTNode?) : super(stub, nodeType, node)
    override val visibleOutsideFunction = true
    override val visibleBeforeDeclaration = true

    override fun getNameIdentifier(): PsiElement? = getChildOfType(MatlabTypes.IDENTIFIER)

    override fun getName(): String? {
        val stub = stub
        if (stub != null) {
            return stub.name
        }
        return nameIdentifier?.text
    }

    override fun setName(name: String): PsiElement {
        nameIdentifier?.replace(createIdentifierFromText(project, name))
        return this
    }

    override fun getTextOffset(): Int = nameIdentifier?.node?.startOffset ?: super.getTextOffset()

    override fun processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        returnValues?.retValueList?.forEach { retValue ->
            if (!processor.execute(retValue, state)) return false
        }

        parameters?.parameterList?.forEach { parameter ->
            if (!processor.execute(parameter, state)) return false
        }
        return true
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(${node.elementType})"
    }

    override fun getType(): MatlabType {
        val cl = this.isConstructor()
        return if (cl != null) MatlabTypeClass(cl) else MatlabTypeFunction(this)
    }
}