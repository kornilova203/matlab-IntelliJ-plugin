// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kornilova203.matlab.psi.MatlabTypes.*;
import com.github.kornilova203.matlab.psi.MatlabCompositePsiElement;
import com.github.kornilova203.matlab.psi.*;
import com.intellij.psi.tree.IElementType;

public class MatlabEventsBlockImpl extends MatlabCompositePsiElement implements MatlabEventsBlock {

  public MatlabEventsBlockImpl(@NotNull IElementType type) {
    super(type);
  }

  public void accept(@NotNull MatlabVisitor visitor) {
    visitor.visitEventsBlock(this);
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
  @Nullable
  public MatlabEventsList getEventsList() {
    return PsiTreeUtil.getChildOfType(this, MatlabEventsList.class);
  }

}
