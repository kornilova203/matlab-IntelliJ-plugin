// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kornilova203.matlab.psi.MatlabTypes.*;
import com.github.kornilova203.matlab.psi.MatlabStubbedClassDeclaration;
import com.github.kornilova203.matlab.psi.*;
import com.github.kornilova203.matlab.stub.MatlabClassDeclarationStub;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;

public class MatlabClassDeclarationImpl extends MatlabStubbedClassDeclaration implements MatlabClassDeclaration {

  public MatlabClassDeclarationImpl(@NotNull MatlabClassDeclarationStub stub, @NotNull IStubElementType<?, ?> type) {
    super(stub, type);
  }

  public MatlabClassDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public MatlabClassDeclarationImpl(@Nullable MatlabClassDeclarationStub stub, @Nullable IElementType type, @Nullable ASTNode node) {
    super(stub, type, node);
  }

  public void accept(@NotNull MatlabVisitor visitor) {
    visitor.visitClassDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MatlabVisitor) accept((MatlabVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public MatlabAttributes getAttributes() {
    return findChildByClass(MatlabAttributes.class);
  }

  @Override
  @NotNull
  public List<MatlabEnumerationBlock> getEnumerationBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MatlabEnumerationBlock.class);
  }

  @Override
  @NotNull
  public List<MatlabEventsBlock> getEventsBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MatlabEventsBlock.class);
  }

  @Override
  @NotNull
  public List<MatlabMethodsBlock> getMethodsBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MatlabMethodsBlock.class);
  }

  @Override
  @NotNull
  public List<MatlabPropertiesBlock> getPropertiesBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MatlabPropertiesBlock.class);
  }

  @Override
  @Nullable
  public MatlabSuperClasses getSuperClasses() {
    return findChildByClass(MatlabSuperClasses.class);
  }

}
