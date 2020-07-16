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

public class MatlabFunctionExprImpl extends MatlabExprImpl implements MatlabFunctionExpr {

  public MatlabFunctionExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MatlabVisitor visitor) {
    visitor.visitFunctionExpr(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MatlabVisitor) accept((MatlabVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public MatlabArguments getArguments() {
    return findNotNullChildByClass(MatlabArguments.class);
  }

  @Override
  @NotNull
  public MatlabExpr getExpr() {
    return findNotNullChildByClass(MatlabExpr.class);
  }

}
