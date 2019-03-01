package com.github.korniloval.matlab.psi

import com.github.korniloval.matlab.psi.MatlabPsiUtil.createIdentifierFromText
import com.github.korniloval.matlab.psi.MatlabPsiUtil.identifier
import com.github.korniloval.matlab.psi.impl.MatlabRefExprImpl
import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator

/**
 * @author Liudmila Kornilova
 */
class MatlabElementManipulator : AbstractElementManipulator<MatlabRefExprImpl>() {
    override fun handleContentChange(psi: MatlabRefExprImpl, range: TextRange, newContent: String): MatlabRefExprImpl? {
        val oldText = psi.text
        val newText = oldText.substring(0, range.startOffset) + newContent + oldText.substring(range.endOffset)
        psi.identifier().replace(createIdentifierFromText(psi.project, newText))
        return psi
    }
}
