package com.github.korniloval.matlab.execution

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.util.text.StringUtil

object OctaveExecutionHelper : ExecutionHelper {
    override fun runFileCommand(path: String) = "run(\"${StringUtil.escapeQuotes(path)}\")"

    override fun appendCommand(cmd: GeneralCommandLine, command: String) {
        cmd.addParameters("--eval", command)
    }
}
