package com.github.korniloval.matlab.execution

import com.intellij.execution.CommonProgramRunConfigurationParameters

interface MatlabRunConfigurationsParams : CommonProgramRunConfigurationParameters {
    fun getInterpreterPath(): String?

    fun setInterpreterPath(path: String)

    fun getPath(): String?

    fun setPath(path: String)

    fun getCommand(): String?

    fun setCommand(command: String)

    fun getInterpreterOptions(): String?

    fun setInterpreterOptions(options: String)
}
