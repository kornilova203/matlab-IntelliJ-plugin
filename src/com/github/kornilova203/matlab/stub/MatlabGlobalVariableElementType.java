package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.MatlabLanguage;
import com.github.kornilova203.matlab.psi.MatlabClassDeclaration;
import com.github.kornilova203.matlab.psi.MatlabGlobalVariableDeclaration;
import com.github.kornilova203.matlab.psi.MatlabTypes;
import com.github.kornilova203.matlab.psi.impl.MatlabClassDeclarationImpl;
import com.github.kornilova203.matlab.psi.impl.MatlabGlobalVariableDeclarationImpl;
import com.intellij.psi.stubs.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MatlabGlobalVariableElementType extends IStubElementType<MatlabGlobalVariableStub, MatlabGlobalVariableDeclaration> {
    public MatlabGlobalVariableElementType(String debugName) {
        super(debugName, MatlabLanguage.Companion.getINSTANCE());
    }

    @Override
    public MatlabGlobalVariableDeclaration createPsi(@NotNull MatlabGlobalVariableStub stub) {
        return new MatlabGlobalVariableDeclarationImpl(stub, (IStubElementType) MatlabTypes.GLOBAL_VARIABLE_DECLARATION);
    }

    @NotNull
    @Override
    public MatlabGlobalVariableStub createStub(@NotNull MatlabGlobalVariableDeclaration psi, StubElement parentStub) {
        MatlabGlobalVariableDeclarationImpl impl = (MatlabGlobalVariableDeclarationImpl)psi;
        return new MatlabGlobalVariableStubImpl(parentStub, (IStubElementType)MatlabTypes.GLOBAL_VARIABLE_DECLARATION, impl.getName());
    }

    @Override
    public @NotNull String getExternalId() {
        return "MatlabGlobalVariable";
    }

    @Override
    public void serialize(@NotNull MatlabGlobalVariableStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public MatlabGlobalVariableStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new MatlabGlobalVariableStubImpl(parentStub, this, dataStream.readNameString());
    }

    @Override
    public void indexStub(@NotNull MatlabGlobalVariableStub stub, @NotNull IndexSink sink) {
        if (stub.getName() != null) {
            sink.occurrence(MatlabGlobalVariableIndex.KEY, stub.getName());
        }
    }
}
