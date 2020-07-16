package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.psi.MatlabFile;
import com.intellij.psi.stubs.PsiFileStub;

public interface MatlabFileStub extends PsiFileStub<MatlabFile> {
    String getName();
}
