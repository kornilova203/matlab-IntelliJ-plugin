package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.psi.MatlabGlobalVariableDeclaration;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;

public class MatlabGlobalVariableStubImpl extends StubBase<MatlabGlobalVariableDeclaration> implements MatlabGlobalVariableStub {
    private final String myName;

    public MatlabGlobalVariableStubImpl(StubElement parent, IStubElementType elementType, String name) {
        super(parent, elementType);
        myName = name;
    }

    @Override
    public String getName() {
        return myName;

    }
}
