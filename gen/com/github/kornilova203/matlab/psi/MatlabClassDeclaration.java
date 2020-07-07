// This is a generated file. Not intended for manual editing.
package com.github.kornilova203.matlab.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MatlabClassDeclaration extends PsiElement {

  @Nullable
  MatlabAttributes getAttributes();

  @NotNull
  List<MatlabEnumerationBlock> getEnumerationBlockList();

  @NotNull
  List<MatlabEventsBlock> getEventsBlockList();

  @NotNull
  List<MatlabMethodsBlock> getMethodsBlockList();

  @NotNull
  List<MatlabPropertiesBlock> getPropertiesBlockList();

  @Nullable
  MatlabSuperClasses getSuperClasses();

}
