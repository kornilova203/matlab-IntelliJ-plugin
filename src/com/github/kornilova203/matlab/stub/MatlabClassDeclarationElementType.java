package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.MatlabLanguage;
import com.github.kornilova203.matlab.psi.MatlabClassDeclaration;
import com.github.kornilova203.matlab.psi.MatlabTypes;
import com.github.kornilova203.matlab.psi.impl.MatlabClassDeclarationImpl;
import com.intellij.psi.stubs.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MatlabClassDeclarationElementType extends IStubElementType<MatlabClassDeclarationStub, MatlabClassDeclaration> {
    public MatlabClassDeclarationElementType(String debugName) {
        super(debugName, MatlabLanguage.Companion.getINSTANCE());
    }

    @Override
    public MatlabClassDeclaration createPsi(@NotNull MatlabClassDeclarationStub stub) {
        return new MatlabClassDeclarationImpl(stub, (IStubElementType) MatlabTypes.CLASS_DECLARATION);
    }

    @NotNull
    @Override
    public MatlabClassDeclarationStub createStub(@NotNull MatlabClassDeclaration psi, StubElement parentStub) {
        MatlabClassDeclarationImpl impl = (MatlabClassDeclarationImpl)psi;
        return new MatlabClassDeclarationStubImpl(parentStub, (IStubElementType)MatlabTypes.CLASS_DECLARATION, impl.getName());
    }

    @Override
    public @NotNull String getExternalId() {
        return "MatlabClassDeclaration";
    }

    @Override
    public void serialize(@NotNull MatlabClassDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public MatlabClassDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new MatlabClassDeclarationStubImpl(parentStub, this, dataStream.readNameString());
    }

    @Override
    public void indexStub(@NotNull MatlabClassDeclarationStub stub, @NotNull IndexSink sink) {
        if (stub.getName() != null) {
            sink.occurrence(MatlabClassDeclarationIndex.KEY, stub.getName());
        }
    }
}
