package com.github.kornilova203.matlab.execution

import com.intellij.openapi.options.SettingsEditor
import javax.swing.JComponent

class MatlabSettingsEditor : SettingsEditor<MatlabRunConfiguration>() {
    private val form = MatlabConfigForm()

    override fun resetEditorFrom(runConfiguration: MatlabRunConfiguration) {
        form.reset(runConfiguration)
        form.resetForm(runConfiguration)
    }

    override fun createEditor(): JComponent = form

    override fun applyEditorTo(runConfiguration: MatlabRunConfiguration) {
        form.applyTo(runConfiguration)
        form.applyToForm(runConfiguration)
    }
}
