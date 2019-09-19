package com.github.korniloval.matlab.debug

import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.frame.XExecutionStack
import com.intellij.xdebugger.frame.XStackFrame

class MatlabStack(private val frames: List<Frame>) : XExecutionStack("") {

    override fun getTopFrame(): XStackFrame? = frames.firstOrNull()

    override fun computeStackFrames(firstFrameIndex: Int, container: XStackFrameContainer?) {
        container?.addStackFrames(frames.subList(firstFrameIndex, frames.size), true)
    }
}

class Frame internal constructor(private val xPosition: XSourcePosition) : XStackFrame() {
    override fun getSourcePosition() = xPosition
}
