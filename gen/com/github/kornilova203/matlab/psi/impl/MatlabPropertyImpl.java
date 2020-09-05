// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kornilova203.matlab.psi.MatlabTypes.*;
import com.github.kornilova203.matlab.psi.MatlabPropertyMixin;
import com.github.kornilova203.matlab.psi.*;

public class MatlabPropertyImpl extends MatlabPropertyMixin implements MatlabProperty {

  public MatlabPropertyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MatlabVisitor visitor) {
    visitor.visitProperty(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MatlabVisitor) accept((MatlabVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public MatlabClassName getClassName() {
    return findChildByClass(MatlabClassName.class);
  }

  @Override
  @Nullable
  public MatlabDefaultValue getDefaultValue() {
    return findChildByClass(MatlabDefaultValue.class);
  }

  @Override
  @Nullable
  public MatlabPropertySize getPropertySize() {
    return findChildByClass(MatlabPropertySize.class);
  }

  @Override
  @Nullable
  public MatlabPropertyValidationFunctions getPropertyValidationFunctions() {
    return findChildByClass(MatlabPropertyValidationFunctions.class);
  }

}
