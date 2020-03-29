package com.github.kornilova203.matlab.highlighting

import com.github.kornilova203.matlab.MatlabLanguage
import com.intellij.codeInsight.highlighting.PairedBraceMatcherAdapter

class MatlabPairedBraceMatcher : PairedBraceMatcherAdapter(MatlabBraceMatcher(), MatlabLanguage.INSTANCE)
