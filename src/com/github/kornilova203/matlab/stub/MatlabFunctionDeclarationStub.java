package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.psi.MatlabFunctionDeclaration;
import com.intellij.psi.stubs.StubElement;

public interface MatlabFunctionDeclarationStub extends StubElement<MatlabFunctionDeclaration> {
    String getName();
}
