package com.github.korniloval.matlab.debug

import com.github.korniloval.matlab.debug.MatlabDebugProcess.Companion.DEBUG_PROMPT
import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.frame.*

class MatlabStack(private val frames: List<Frame>) : XExecutionStack("") {

    override fun getTopFrame(): XStackFrame? = frames.firstOrNull()

    override fun computeStackFrames(firstFrameIndex: Int, container: XStackFrameContainer?) {
        container?.addStackFrames(frames.subList(firstFrameIndex, frames.size), true)
    }
}

class Frame internal constructor(private val xPosition: XSourcePosition, private val debugProcess: MatlabDebugProcess) : XStackFrame() {
    override fun getSourcePosition() = xPosition

    override fun computeChildren(node: XCompositeNode) {
        val varNames = mutableListOf<String>()
        debugProcess.addTextListener(ProcessOutputTypes.STDOUT) { text ->
            if (text == DEBUG_PROMPT) {
                val children = XValueChildrenList()
                varNames.forEach {
                    children.add(MatlabValue(it))
                }
                node.addChildren(children, true)
                return@addTextListener false
            }
            val matchResult = VAR_DESC_PATTERN.matchEntire(text) ?: return@addTextListener true
            val attr = matchResult.groups["attr"]?.value ?: ""
            if (attr.contains(AUTO_VAR_ATTR)) return@addTextListener true
            matchResult.groups["name"]?.value?.let { varNames.add(it) }
            true
        }
        debugProcess.command("whos")
    }

    companion object {
        private val VAR_DESC_PATTERN = Regex("\\s+(?<attr>\\w+)?\\s+(?<name>\\w+)\\s+(?<size>\\d+x\\d+)\\s+(?<bytes>\\d+)\\s+(?<class>\\w+)\n")
        private const val AUTO_VAR_ATTR = 'a'
    }
}

class MatlabValue(name: String) : XNamedValue(name) {
    override fun computePresentation(node: XValueNode, place: XValuePlace) {

    }
}
