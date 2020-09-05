package com.github.kornilova203.matlab.psi

import com.github.kornilova203.matlab.psi.types.MatlabType
import com.github.kornilova203.matlab.psi.types.MatlabTypeClass
import com.github.kornilova203.matlab.stub.MatlabClassDeclarationStub
import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.StubBasedPsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.tree.IElementType

abstract class MatlabStubbedClassDeclaration : StubBasedPsiElementBase<MatlabClassDeclarationStub?>,
        StubBasedPsiElement<MatlabClassDeclarationStub>, MatlabDeclaration, MatlabClassDeclaration, MatlabTypedExpr {
    constructor(stub: MatlabClassDeclarationStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)
    constructor(node: ASTNode) : super(node)
    constructor(stub: MatlabClassDeclarationStub?, nodeType: IElementType?, node: ASTNode?) : super(stub, nodeType, node)
    override val visibleOutsideFunction = true

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

    override fun toString(): String {
        return "${javaClass.simpleName}(${node.elementType})"
    }

    override fun getType(): MatlabType = MatlabTypeClass(this)
}