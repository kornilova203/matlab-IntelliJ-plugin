package com.github.korniloval.matlab.debug

import com.github.korniloval.matlab.execution.MatlabCommandLineState
import com.github.korniloval.matlab.execution.MatlabRunConfiguration
import com.intellij.execution.ExecutionResult
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessListener
import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.ui.ExecutionConsole
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.xdebugger.XDebugProcess
import com.intellij.xdebugger.XDebugSession
import com.intellij.xdebugger.XDebuggerUtil
import com.intellij.xdebugger.breakpoints.XBreakpointHandler
import com.intellij.xdebugger.breakpoints.XLineBreakpoint
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider
import com.intellij.xdebugger.frame.XSuspendContext

class MatlabDebugProcess(session: XDebugSession, private val state: MatlabCommandLineState, private val env: ExecutionEnvironment) : XDebugProcess(session) {
    private val provider = MatlabDebuggerEditorsProvider()
    private val executionResult: ExecutionResult = state.execute(env.executor, env.runner)
    private val breakpointHandler = MatlabBreakpointHandler()

    override fun getEditorsProvider(): XDebuggerEditorsProvider = provider

    override fun sessionInitialized() {
        initListener()
        executionResult.processHandler.startNotify()
        command("PS1(\"\")")
        initBreakpoints(breakpointHandler.breakpoints)

        val config = env.runProfile as MatlabRunConfiguration
        command(config.getCommand()!!)
    }

    private fun initBreakpoints(breakpoints: MutableSet<XLineBreakpoint<MatlabLineBreakpointProperties>>) {
        for (breakpoint in breakpoints) {
            val funcName = file(breakpoint.fileUrl)?.nameWithoutExtension ?: return
            command("$STOP $funcName ${breakpoint.line + 1}")
        }
    }

    private fun initListener() {
        executionResult.processHandler.addProcessListener(object : ProcessListener {
            override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
                if (outputType != ProcessOutputTypes.STDERR) return
                val res = STOPPED_PATTERN.matchEntire(event.text) ?: return
                val filePath = "file://${res.groupValues[1]}"
                val lineNum = res.groupValues[2].toInt() - 1
                val bp = breakpointHandler.breakpoints.find {
                    it.fileUrl == filePath && it.line == lineNum
                } ?: return
                val frames = listOf(Frame(XDebuggerUtil.getInstance().createPosition(file(bp.fileUrl), lineNum)!!))
                session.breakpointReached(bp, null, MatlabSuspendContext(MatlabStack(frames)))
            }

            override fun processTerminated(event: ProcessEvent) = Unit
            override fun processWillTerminate(event: ProcessEvent, willBeDestroyed: Boolean) = Unit
            override fun startNotified(event: ProcessEvent) = Unit
        })
    }

    private fun file(fileUrl: String) = VirtualFileManager.getInstance().findFileByUrl(fileUrl)

    private fun command(command: String) {
        val input = executionResult.processHandler.processInput ?: return
        try {
            input.write("$command\n".toByteArray())
        } finally {
            input.flush()
        }
    }

    override fun createConsole(): ExecutionConsole {
        return executionResult.executionConsole ?: super.createConsole()
    }

    override fun getBreakpointHandlers(): Array<XBreakpointHandler<*>> = arrayOf(breakpointHandler)

    override fun resume(context: XSuspendContext?) {
        command(CONT)
    }

    companion object {
        private const val STOP = "dbstop"
        private const val CONT = "dbcont"
        private val STOPPED_PATTERN = Regex("stopped in (.*) at line (\\d+)\n")
    }
}
