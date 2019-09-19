package com.github.korniloval.matlab.debug

import com.intellij.xdebugger.frame.XSuspendContext

class MatlabSuspendContext(private val stack: MatlabStack) : XSuspendContext() {
    override fun getActiveExecutionStack() = stack
}
