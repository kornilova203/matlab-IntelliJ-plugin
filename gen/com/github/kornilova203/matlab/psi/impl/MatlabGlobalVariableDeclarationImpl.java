// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kornilova203.matlab.psi.MatlabTypes.*;
import com.github.kornilova203.matlab.psi.MatlabStubbedGlobalVariable;
import com.github.kornilova203.matlab.psi.*;
import com.github.kornilova203.matlab.stub.MatlabGlobalVariableStub;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;

public class MatlabGlobalVariableDeclarationImpl extends MatlabStubbedGlobalVariable implements MatlabGlobalVariableDeclaration {

  public MatlabGlobalVariableDeclarationImpl(@NotNull MatlabGlobalVariableStub stub, @NotNull IStubElementType<?, ?> type) {
    super(stub, type);
  }

  public MatlabGlobalVariableDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public MatlabGlobalVariableDeclarationImpl(@Nullable MatlabGlobalVariableStub stub, @Nullable IElementType type, @Nullable ASTNode node) {
    super(stub, type, node);
  }

  public void accept(@NotNull MatlabVisitor visitor) {
    visitor.visitGlobalVariableDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MatlabVisitor) accept((MatlabVisitor)visitor);
    else super.accept(visitor);
  }

}
