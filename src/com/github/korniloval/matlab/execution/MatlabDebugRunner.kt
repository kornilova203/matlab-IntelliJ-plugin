package com.github.korniloval.matlab.execution

import com.github.korniloval.matlab.debug.MatlabDebugProcess
import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.executors.DefaultDebugExecutor
import com.intellij.execution.runners.DefaultProgramRunner
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.xdebugger.XDebugProcess
import com.intellij.xdebugger.XDebugProcessStarter
import com.intellij.xdebugger.XDebugSession
import com.intellij.xdebugger.XDebuggerManager

class MatlabDebugRunner : DefaultProgramRunner() {
    override fun getRunnerId(): String = MatlabDebugRunner::class.java.simpleName

    override fun canRun(executorId: String, profile: RunProfile): Boolean {
        return DefaultDebugExecutor.EXECUTOR_ID == executorId && profile is MatlabRunConfiguration
    }

    override fun doExecute(state: RunProfileState, environment: ExecutionEnvironment): RunContentDescriptor? {
        if (state !is MatlabCommandLineState) return null
        val debuggerManager = XDebuggerManager.getInstance(environment.project)
        val debugProcessStarter = object : XDebugProcessStarter() {
            override fun start(session: XDebugSession): XDebugProcess {
                return MatlabDebugProcess(session, state, environment)
            }
        }
        return debuggerManager.startSession(environment, debugProcessStarter).runContentDescriptor
    }
}