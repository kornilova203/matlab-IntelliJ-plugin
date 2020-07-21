package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.psi.MatlabGlobalVariableDeclaration;
import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndexKey;
import org.jetbrains.annotations.NotNull;

public class MatlabGlobalVariableIndex extends StringStubIndexExtension<MatlabGlobalVariableDeclaration> {
    public static final StubIndexKey<String, MatlabGlobalVariableDeclaration> KEY = StubIndexKey.createIndexKey("matlab.global.variable");

    @Override
    public @NotNull StubIndexKey<String, MatlabGlobalVariableDeclaration> getKey() {
        return KEY;
    }

    @Override
    public int getVersion() {
        return super.getVersion() + MatlabIndexVersion.INDEX_VERSION;
    }
}