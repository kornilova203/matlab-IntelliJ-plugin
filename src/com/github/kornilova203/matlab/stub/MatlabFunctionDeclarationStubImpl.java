package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.psi.MatlabFunctionDeclaration;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;

public class MatlabFunctionDeclarationStubImpl extends StubBase<MatlabFunctionDeclaration> implements MatlabFunctionDeclarationStub {
    private final String myName;

    public MatlabFunctionDeclarationStubImpl(StubElement parent, IStubElementType elementType, String name) {
        super(parent, elementType);
        myName = name;
    }

    @Override
    public String getName() {
        return myName;
    }
}
