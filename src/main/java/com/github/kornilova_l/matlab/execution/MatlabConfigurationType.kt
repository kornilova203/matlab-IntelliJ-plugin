package com.github.kornilova_l.matlab.execution

import com.github.kornilova_l.matlab.Icons
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationTypeBase
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project

class MatlabConfigurationType
    : ConfigurationTypeBase("MatlabApplication", "Matlab", null, Icons.matlabIcon) {
    init {
        addFactory(object : ConfigurationFactory(this) {
            override fun createTemplateConfiguration(project: Project): RunConfiguration = MatlabRunConfiguration(project, this)

            override fun createConfiguration(name: String?, template: RunConfiguration?): RunConfiguration {
                val runConfiguration = super.createConfiguration(name, template)
                if (runConfiguration is MatlabRunConfiguration) {
                    /* set default interpreter path */
                    val path = PropertiesComponent.getInstance(runConfiguration.project).getValue(MatlabRunConfiguration.MATLAB_INTERPRETER)
                    if (path != null) {
                        runConfiguration.setInterpreterPath(path)
                    }
                }
                return runConfiguration
            }
        })
    }

    companion object {
        fun getInstance(): MatlabConfigurationType = ConfigurationTypeUtil.findConfigurationType(MatlabConfigurationType::class.java)
    }
}