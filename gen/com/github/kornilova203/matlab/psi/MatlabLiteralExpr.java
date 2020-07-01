// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MatlabLiteralExpr extends MatlabExpr {

  @Nullable
  MatlabCellArrayLiteral getCellArrayLiteral();

  @Nullable
  MatlabMatrixLiteral getMatrixLiteral();

  @Nullable
  PsiElement getFloat();

  @Nullable
  PsiElement getFloatExponential();

  @Nullable
  PsiElement getInteger();

}
