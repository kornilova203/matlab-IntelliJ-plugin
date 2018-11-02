package com.github.korniloval.matlab.execution

import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.KillableColoredProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.util.ProgramParametersUtil


class MatlabCommandLineState(private val runConfiguration: MatlabRunConfiguration, environment: ExecutionEnvironment)
    : CommandLineState(environment) {

    override fun startProcess(): ProcessHandler {
        val workingDir = findWorkingDir(runConfiguration)
        val cmd = createCommandLine(workingDir, runConfiguration)

        val processHandler = KillableColoredProcessHandler(cmd)
        ProcessTerminatedListener.attach(processHandler, environment.project)

        // FIXME: handle path macros
        return processHandler
    }

    private fun createCommandLine(workingDir: String, runConfiguration: MatlabRunConfiguration): GeneralCommandLine {
        val cmd = GeneralCommandLine()
        val interpreter = runConfiguration.getInterpreterPath()!!
        cmd.exePath = interpreter

        if (!runConfiguration.getInterpreterOptions().isNullOrBlank()) {
            cmd.addParameters(runConfiguration.getInterpreterOptions()!!.split(" "))
        }

        if (interpreter.contains("matlab")) {
            cmd.addParameters(mutableListOf("-nodisplay", "-nosplash", "-nodesktop", "-r"))
            cmd.addParameter("run('${runConfiguration.getFilePath()}');exit;")
        } else {
            cmd.parametersList.addParametersString(runConfiguration.getFilePath())
        }

        cmd.withWorkDirectory(workingDir)
        cmd.withParentEnvironmentType(if (runConfiguration.isPassParentEnvs) GeneralCommandLine.ParentEnvironmentType.CONSOLE else GeneralCommandLine.ParentEnvironmentType.NONE)
        cmd.withEnvironment(runConfiguration.envs)
        return cmd
    }

    private fun findWorkingDir(runConfig: MatlabRunConfiguration): String {
        return ProgramParametersUtil.getWorkingDir(runConfig, runConfig.project, null)
    }
}
