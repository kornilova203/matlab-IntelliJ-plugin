package com.github.korniloval.matlab.execution

import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.runners.DefaultProgramRunner

class MatlabProgramRunner : DefaultProgramRunner() {
    override fun canRun(executorId: String, profile: RunProfile): Boolean = DefaultRunExecutor.EXECUTOR_ID == executorId

    override fun getRunnerId(): String = MATLAB_PROGRAM_RUNNER_ID

    companion object {
        const val MATLAB_PROGRAM_RUNNER_ID = "MatlabProgramRunner"
    }
}