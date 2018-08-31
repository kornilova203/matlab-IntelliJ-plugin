package com.github.korniloval.matlab.highlighting

import com.github.korniloval.matlab.MatlabLanguage
import com.intellij.codeInsight.highlighting.PairedBraceMatcherAdapter

class MatlabPairedBraceMatcher : PairedBraceMatcherAdapter(MatlabBraceMatcher(), MatlabLanguage.INSTANCE)
