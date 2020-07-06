// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kornilova203.matlab.psi.MatlabTypes.*;
import com.github.kornilova203.matlab.psi.MatlabDeclarationBase;
import com.github.kornilova203.matlab.psi.*;
import com.intellij.psi.tree.IElementType;

public class MatlabClassDeclarationImpl extends MatlabDeclarationBase implements MatlabClassDeclaration {

  public MatlabClassDeclarationImpl(@NotNull IElementType type) {
    super(type);
  }

  public void accept(@NotNull MatlabVisitor visitor) {
    visitor.visitClassDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MatlabVisitor) accept((MatlabVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public MatlabAttributes getAttributes() {
    return PsiTreeUtil.getChildOfType(this, MatlabAttributes.class);
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
    return PsiTreeUtil.getChildOfType(this, MatlabSuperClasses.class);
  }

}
