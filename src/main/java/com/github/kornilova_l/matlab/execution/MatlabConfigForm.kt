package com.github.kornilova_l.matlab.execution

import com.intellij.execution.ui.CommonProgramParametersPanel
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.LabeledComponent
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.MacroAwareTextBrowseFolderListener
import com.intellij.ui.RawCommandLineEditor
import java.awt.BorderLayout
import javax.swing.JComponent


class MatlabConfigForm : CommonProgramParametersPanel() {
    private var interpreterOptionsComponent: LabeledComponent<RawCommandLineEditor>? = null
    private var interpreterPathComponent: LabeledComponent<JComponent>? = null
    private var interpreterPathField: TextFieldWithBrowseButton? = null

    private var filePathComponent: LabeledComponent<JComponent>? = null
    private var filePathField: TextFieldWithBrowseButton? = null

    private fun initOwnComponents() {

        interpreterOptionsComponent = LabeledComponent.create(RawCommandLineEditor(), "Interpreter options")
        interpreterOptionsComponent!!.labelLocation = BorderLayout.WEST

        val chooseInterpreterDescriptor = FileChooserDescriptorFactory.createSingleLocalFileDescriptor()
        chooseInterpreterDescriptor.title = "Choose interpreter..."

        interpreterPathField = TextFieldWithBrowseButton()
        interpreterPathField!!.addBrowseFolderListener(MacroAwareTextBrowseFolderListener(chooseInterpreterDescriptor, project))
        interpreterPathComponent = LabeledComponent.create(createComponentWithMacroBrowse(interpreterPathField!!), "Interpreter path:")
        interpreterPathComponent!!.labelLocation = BorderLayout.WEST


        val chooseScriptDescriptor = FileChooserDescriptorFactory.createSingleLocalFileDescriptor()
        filePathField = TextFieldWithBrowseButton()
        filePathField!!.addBrowseFolderListener(MacroAwareTextBrowseFolderListener(chooseScriptDescriptor, project))

        filePathComponent = LabeledComponent.create(createComponentWithMacroBrowse(filePathField!!), "File:")
        filePathComponent!!.labelLocation = BorderLayout.WEST
    }


    override fun addComponents() {
        initOwnComponents()

        add(filePathComponent)
        add(interpreterPathComponent)
        add(interpreterOptionsComponent)

        super.addComponents()
    }

    fun resetForm(configuration: MatlabRunConfiguration) {
        interpreterOptionsComponent!!.component.text = configuration.getInterpreterOptions()
        interpreterPathField!!.setText(configuration.getInterpreterPath())
        filePathField!!.setText(configuration.getFilePath())
    }

    fun applyToForm(configuration: MatlabRunConfiguration) {
        configuration.setInterpreterOptions(interpreterOptionsComponent!!.component.text)
        configuration.setInterpreterPath(interpreterPathField!!.text)
        configuration.setFilePath(filePathField!!.text)
    }
}