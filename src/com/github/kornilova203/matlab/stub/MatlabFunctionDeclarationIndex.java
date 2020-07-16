package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.psi.MatlabFunctionDeclaration;
import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndexKey;
import org.jetbrains.annotations.NotNull;

public class MatlabFunctionDeclarationIndex extends StringStubIndexExtension<MatlabFunctionDeclaration> {
    public static final StubIndexKey<String, MatlabFunctionDeclaration> KEY = StubIndexKey.createIndexKey("matlab.function.declaration");

    @Override
    public @NotNull StubIndexKey<String, MatlabFunctionDeclaration> getKey() {
        return KEY;
    }
}
