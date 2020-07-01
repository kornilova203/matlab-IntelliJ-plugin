// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kornilova203.matlab.psi.MatlabTypes.*;
import com.github.kornilova203.matlab.psi.*;
import com.intellij.psi.tree.IElementType;

public class MatlabLiteralExprImpl extends MatlabExprImpl implements MatlabLiteralExpr {

  public MatlabLiteralExprImpl(@NotNull IElementType type) {
    super(type);
  }

  public void accept(@NotNull MatlabVisitor visitor) {
    visitor.visitLiteralExpr(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MatlabVisitor) accept((MatlabVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public MatlabCellArrayLiteral getCellArrayLiteral() {
    return PsiTreeUtil.getChildOfType(this, MatlabCellArrayLiteral.class);
  }

  @Override
  @Nullable
  public MatlabMatrixLiteral getMatrixLiteral() {
    return PsiTreeUtil.getChildOfType(this, MatlabMatrixLiteral.class);
  }

  @Override
  @Nullable
  public PsiElement getFloat() {
    return findPsiChildByType(FLOAT);
  }

  @Override
  @Nullable
  public PsiElement getFloatExponential() {
    return findPsiChildByType(FLOAT_EXPONENTIAL);
  }

  @Override
  @Nullable
  public PsiElement getInteger() {
    return findPsiChildByType(INTEGER);
  }

}
