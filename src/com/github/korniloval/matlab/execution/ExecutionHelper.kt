package com.github.korniloval.matlab.execution

import com.intellij.execution.configurations.GeneralCommandLine

sealed class ExecutionHelper {
    abstract fun initCmd(cmd: GeneralCommandLine, config: MatlabRunConfiguration)
}

object OctaveRunHelper : ExecutionHelper() {
    override fun initCmd(cmd: GeneralCommandLine, config: MatlabRunConfiguration) {
        cmd.addParameters("--eval", config.getCommand())
    }
}

object OctaveDebugHelper : ExecutionHelper() {
    override fun initCmd(cmd: GeneralCommandLine, config: MatlabRunConfiguration) {
        cmd.addParameters("-i", "--quiet", "--no-gui")
    }
}

fun isMatlab(interpreterPath: String) = interpreterPath.contains("matlab")

fun guessExecutionHelper(isMatlab: Boolean, isDebug: Boolean) = when {
    isDebug -> OctaveDebugHelper
    else -> OctaveRunHelper
}
