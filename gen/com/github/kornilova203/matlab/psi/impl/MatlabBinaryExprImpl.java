// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kornilova203.matlab.psi.MatlabTypes.*;
import com.github.kornilova203.matlab.psi.MatlabBinaryExprMixin;
import com.github.kornilova203.matlab.psi.*;

public class MatlabBinaryExprImpl extends MatlabBinaryExprMixin implements MatlabBinaryExpr {

  public MatlabBinaryExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MatlabVisitor visitor) {
    visitor.visitBinaryExpr(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MatlabVisitor) accept((MatlabVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<MatlabExpr> getExprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MatlabExpr.class);
  }

  @Override
  @NotNull
  public MatlabExpr getLeft() {
    List<MatlabExpr> p1 = getExprList();
    return p1.get(0);
  }

  @Override
  @Nullable
  public MatlabExpr getRight() {
    List<MatlabExpr> p1 = getExprList();
    return p1.size() < 2 ? null : p1.get(1);
  }

}
