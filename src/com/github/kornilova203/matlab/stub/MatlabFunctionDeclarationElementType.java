package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.MatlabLanguage;
import com.github.kornilova203.matlab.psi.MatlabFunctionDeclaration;
import com.github.kornilova203.matlab.psi.MatlabTypes;
import com.github.kornilova203.matlab.psi.impl.MatlabFunctionDeclarationImpl;
import com.intellij.psi.stubs.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MatlabFunctionDeclarationElementType  extends IStubElementType<MatlabFunctionDeclarationStub, MatlabFunctionDeclaration> {
    public MatlabFunctionDeclarationElementType(String debugName) {
        super(debugName, MatlabLanguage.Companion.getINSTANCE());
    }

    @Override
    public MatlabFunctionDeclaration createPsi(@NotNull MatlabFunctionDeclarationStub stub) {
        return new MatlabFunctionDeclarationImpl(stub, (IStubElementType) MatlabTypes.FUNCTION_DECLARATION);
    }

    @NotNull
    @Override
    public MatlabFunctionDeclarationStub createStub(@NotNull MatlabFunctionDeclaration psi, StubElement parentStub) {
        MatlabFunctionDeclarationImpl impl = (MatlabFunctionDeclarationImpl)psi;
        return new MatlabFunctionDeclarationStubImpl(parentStub, (IStubElementType)MatlabTypes.FUNCTION_DECLARATION, impl.getName());
    }

    @Override
    public @NotNull String getExternalId() {
        return "MatlabFunctionDeclaration";
    }

    @Override
    public void serialize(@NotNull MatlabFunctionDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public MatlabFunctionDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new MatlabFunctionDeclarationStubImpl(parentStub, this, dataStream.readNameString());
    }

    @Override
    public void indexStub(@NotNull MatlabFunctionDeclarationStub stub, @NotNull IndexSink sink) {
        if (stub.getName() != null) {
            sink.occurrence(MatlabFunctionDeclarationIndex.KEY, stub.getName());
        }
    }
}
