// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kornilova203.matlab.psi.MatlabTypes.*;
import com.github.kornilova203.matlab.psi.MatlabLiteralExprMixin;
import com.github.kornilova203.matlab.psi.*;

public class MatlabLiteralExprImpl extends MatlabLiteralExprMixin implements MatlabLiteralExpr {

  public MatlabLiteralExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MatlabVisitor visitor) {
    visitor.visitLiteralExpr(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MatlabVisitor) accept((MatlabVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public MatlabCellArrayLiteral getCellArrayLiteral() {
    return findChildByClass(MatlabCellArrayLiteral.class);
  }

  @Override
  @Nullable
  public MatlabMatrixLiteral getMatrixLiteral() {
    return findChildByClass(MatlabMatrixLiteral.class);
  }

  @Override
  @Nullable
  public PsiElement getFloat() {
    return findChildByType(FLOAT);
  }

  @Override
  @Nullable
  public PsiElement getFloatExponential() {
    return findChildByType(FLOAT_EXPONENTIAL);
  }

  @Override
  @Nullable
  public PsiElement getInteger() {
    return findChildByType(INTEGER);
  }

}
