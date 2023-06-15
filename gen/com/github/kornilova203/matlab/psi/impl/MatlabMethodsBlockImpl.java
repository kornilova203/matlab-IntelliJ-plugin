// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kornilova203.matlab.psi.MatlabTypes.*;
import com.github.kornilova203.matlab.psi.MatlabASTWrapperPsiElement;
import com.github.kornilova203.matlab.psi.*;

public class MatlabMethodsBlockImpl extends MatlabASTWrapperPsiElement implements MatlabMethodsBlock {

  public MatlabMethodsBlockImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MatlabVisitor visitor) {
    visitor.visitMethodsBlock(this);
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
  @Nullable
  public MatlabMethodsList getMethodsList() {
    return findChildByClass(MatlabMethodsList.class);
  }

}
