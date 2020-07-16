// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kornilova203.matlab.psi.MatlabTypes.*;
import com.github.kornilova203.matlab.psi.MatlabStubbedFunctionDeclaration;
import com.github.kornilova203.matlab.psi.*;
import com.github.kornilova203.matlab.stub.MatlabFunctionDeclarationStub;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;

public class MatlabFunctionDeclarationImpl extends MatlabStubbedFunctionDeclaration implements MatlabFunctionDeclaration {

  public MatlabFunctionDeclarationImpl(@NotNull MatlabFunctionDeclarationStub stub, @NotNull IStubElementType<?, ?> nodeType) {
    super(stub, nodeType);
  }

  public MatlabFunctionDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public MatlabFunctionDeclarationImpl(@Nullable MatlabFunctionDeclarationStub stub, @Nullable IElementType type, @Nullable ASTNode node) {
    super(stub, type, node);
  }

  public void accept(@NotNull MatlabVisitor visitor) {
    visitor.visitFunctionDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MatlabVisitor) accept((MatlabVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public MatlabBlock getBlock() {
    return findChildByClass(MatlabBlock.class);
  }

  @Override
  @Nullable
  public MatlabGetterOrSetterModifier getGetterOrSetterModifier() {
    return findChildByClass(MatlabGetterOrSetterModifier.class);
  }

  @Override
  @Nullable
  public MatlabParameters getParameters() {
    return findChildByClass(MatlabParameters.class);
  }

  @Override
  @Nullable
  public MatlabReturnValues getReturnValues() {
    return findChildByClass(MatlabReturnValues.class);
  }

}
