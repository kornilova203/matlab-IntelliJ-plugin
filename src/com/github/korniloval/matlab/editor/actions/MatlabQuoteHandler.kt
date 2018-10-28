package com.github.korniloval.matlab.editor.actions

import com.github.korniloval.matlab.psi.MatlabTypes
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler
import com.intellij.psi.tree.TokenSet

class MatlabQuoteHandler : SimpleTokenSetQuoteHandler(
        TokenSet.create(MatlabTypes.SINGLE_QUOTE_STRING, MatlabTypes.DOUBLE_QUOTE_STRING))
