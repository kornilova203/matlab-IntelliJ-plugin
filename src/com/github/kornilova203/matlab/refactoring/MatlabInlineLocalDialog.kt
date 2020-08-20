package com.github.kornilova203.matlab.refactoring

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiElement
import com.intellij.refactoring.inline.InlineOptionsDialog

class MatlabInlineLocalDialog(project: Project, 
                              private val variable: PsiElement,
                              private val occurrencesCount: Int) : InlineOptionsDialog(project, true, variable) {    
    init {
        title = "Inline variable"
        myInvokedOnReference = true
        init()
    }

    override fun doAction() {
        close(DialogWrapper.OK_EXIT_CODE)
    }

    override fun getBorderTitle(): String = "Inline"

    override fun getNameLabelText(): String = "Variable '${variable.text}'"

    override fun getInlineAllText(): String? {
        val occurrencesString = if (occurrencesCount > -1) " (" + occurrencesCount + " occurrence" + (if (occurrencesCount == 1) ")" else "s)") else ""
        return "Inline all references and remove the variable$occurrencesString"
    }

    override fun getInlineThisText(): String = "Inline this reference only and keep the variable"

    override fun isInlineThis(): Boolean = false
    
    override fun getHelpId(): String? = "refactoring.inlineVariable"

    override fun hasPreviewButton(): Boolean = false
}