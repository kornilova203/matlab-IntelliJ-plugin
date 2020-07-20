package com.github.kornilova203.matlab.execution

import com.github.kornilova203.matlab.Icons
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.SimpleConfigurationType
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NotNullLazyValue

class MatlabConfigurationType
    : SimpleConfigurationType("MatlabApplication", "Matlab", null, NotNullLazyValue.createConstantValue(Icons.Matlab)) {

    override fun createConfiguration(name: String?, template: RunConfiguration): RunConfiguration {
        val runConfiguration = super.createConfiguration(name, template)
        if (runConfiguration is MatlabRunConfiguration) {
            /* set default interpreter path */
            val path = PropertiesComponent.getInstance().getValue(MatlabRunConfiguration.MATLAB_INTERPRETER)
            if (path != null) {
                runConfiguration.setInterpreterPath(path)
            }
        }
        return runConfiguration
    }

    override fun createTemplateConfiguration(project: Project): RunConfiguration {
        return MatlabRunConfiguration(project, this)
    }
}