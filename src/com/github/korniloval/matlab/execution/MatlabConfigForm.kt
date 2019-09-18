package com.github.korniloval.matlab.execution

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

    private var commandComponent: LabeledComponent<JComponent>? = null
    private var commandField: RawCommandLineEditor? = null

    private var pathComponent: LabeledComponent<JComponent>? = null
    private var pathField: RawCommandLineEditor? = null

    private fun initOwnComponents() {

        interpreterOptionsComponent = LabeledComponent.create(RawCommandLineEditor(), "Interpreter options")
        interpreterOptionsComponent!!.labelLocation = BorderLayout.WEST

        val chooseInterpreterDescriptor = FileChooserDescriptorFactory.createSingleLocalFileDescriptor()
        chooseInterpreterDescriptor.title = "Choose interpreter..."

        interpreterPathField = TextFieldWithBrowseButton()
        interpreterPathField!!.addBrowseFolderListener(MacroAwareTextBrowseFolderListener(chooseInterpreterDescriptor, project))
        interpreterPathComponent = LabeledComponent.create(createComponentWithMacroBrowse(interpreterPathField!!), "Interpreter path:")
        interpreterPathComponent!!.labelLocation = BorderLayout.WEST


        pathField = RawCommandLineEditor()
        pathComponent = LabeledComponent.create(pathField!!, "Path")
        pathComponent!!.labelLocation = BorderLayout.WEST

        commandField = RawCommandLineEditor()
        commandComponent = LabeledComponent.create(commandField!!, "Matlab/Octave Command")
        commandComponent!!.labelLocation = BorderLayout.WEST
    }


    override fun addComponents() {
        initOwnComponents()

        add(commandComponent)
        add(pathComponent)
        add(interpreterPathComponent)
        add(interpreterOptionsComponent)

        super.addComponents()
    }

    fun resetForm(configuration: MatlabRunConfiguration) {
        interpreterOptionsComponent!!.component.text = configuration.getInterpreterOptions()
        interpreterPathField!!.setText(configuration.getInterpreterPath())
        pathField!!.setText(configuration.getPath())
        commandField!!.setText(configuration.getCommand())
    }

    fun applyToForm(configuration: MatlabRunConfiguration) {
        configuration.setInterpreterOptions(interpreterOptionsComponent!!.component.text)
        configuration.setInterpreterPath(interpreterPathField!!.text)
        configuration.setCommand(commandField!!.text)
    }
}