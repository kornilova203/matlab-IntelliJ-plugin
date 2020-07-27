package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.psi.MatlabClassDeclaration;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;

public class MatlabClassDeclarationStubImpl extends StubBase<MatlabClassDeclaration> implements MatlabClassDeclarationStub {
    private final String myName;

    public MatlabClassDeclarationStubImpl(StubElement parent, IStubElementType elementType, String name) {
        super(parent, elementType);
        myName = name;
    }

    @Override
    public String getName() {
        return myName;
    }
}
