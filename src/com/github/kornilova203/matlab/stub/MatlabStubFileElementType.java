package com.github.kornilova203.matlab.stub;

import com.github.kornilova203.matlab.MatlabLanguage;
import com.intellij.lang.Language;
import com.intellij.psi.tree.IStubFileElementType;

public class MatlabStubFileElementType extends IStubFileElementType<MatlabFileStub> {
    public MatlabStubFileElementType() {
        super(MatlabLanguage.Companion.getINSTANCE());
    }
}
