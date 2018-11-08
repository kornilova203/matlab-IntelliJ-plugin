package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.psi.impl.MatlabRefImpl
import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator

/**
 * @author Liudmila Kornilova
 */
class MatlabElementManipulator : AbstractElementManipulator<MatlabRefImpl>() {
    override fun handleContentChange(psi: MatlabRefImpl, range: TextRange, newContent: String): MatlabRefImpl? {
        val oldText = psi.text
        val newText = oldText.substring(0, range.startOffset) + newContent + oldText.substring(range.endOffset)
        psi.setName(newText)
        return psi
    }
}
