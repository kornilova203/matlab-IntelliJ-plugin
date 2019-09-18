package com.github.korniloval.matlab.execution

import com.intellij.execution.configurations.GeneralCommandLine

interface ExecutionHelper {
    fun runFileCommand(path: String): String
    fun appendCommand(cmd: GeneralCommandLine, command: String)
}