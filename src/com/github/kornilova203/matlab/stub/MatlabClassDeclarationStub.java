package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.psi.MatlabClassDeclaration;
import com.intellij.psi.stubs.StubElement;

public interface MatlabClassDeclarationStub extends StubElement<MatlabClassDeclaration> {
    String getName();
}
