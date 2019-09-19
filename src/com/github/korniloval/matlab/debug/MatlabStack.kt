package com.github.korniloval.matlab.debug

import com.intellij.icons.AllIcons
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
        val children = XValueChildrenList()
        debugProcess.commandsQueue.queue.add(object : Command("whos") {
            override fun process(text: String) {
                val matchResult = VAR_DESC_PATTERN.matchEntire(text) ?: return
                val attr = matchResult.groups["attr"]?.value ?: ""
                if (attr.contains(AUTO_VAR_ATTR)) return
                val type = matchResult.groups["class"]?.value
                matchResult.groups["name"]?.value?.let { children.add(MatlabValue(it, type)) }
            }

            override fun end() {
                node.addChildren(children, true)
            }
        })
    }

    companion object {
        private val VAR_DESC_PATTERN = Regex("\\s+(?<attr>\\w+)?\\s+(?<name>\\w+)\\s+(?<size>\\d+x\\d+)\\s+(?<bytes>\\d+)\\s+(?<class>\\w+)\n")
        private const val AUTO_VAR_ATTR = 'a'
    }
}

class MatlabValue(name: String, private val type: String?) : XNamedValue(name) {
    override fun computePresentation(node: XValueNode, place: XValuePlace) {
        node.setPresentation(AllIcons.Nodes.Variable, type, "", false)
    }
}
