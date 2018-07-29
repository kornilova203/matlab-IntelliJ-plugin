package com.github.kornilova_l.matlab.execution

import com.intellij.execution.CommonProgramRunConfigurationParameters

interface MatlabRunConfigurationsParams : CommonProgramRunConfigurationParameters {
    fun getInterpreterPath(): String?

    fun setInterpreterPath(path: String)

    fun getFilePath(): String?

    fun setFilePath(path: String)

    fun getInterpreterOptions(): String?

    fun setInterpreterOptions(options: String)
}
