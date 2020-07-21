package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.psi.MatlabGlobalVariableDeclaration;
import com.intellij.psi.stubs.StubElement;

public interface MatlabGlobalVariableStub extends StubElement<MatlabGlobalVariableDeclaration> {
    String getName();
}
