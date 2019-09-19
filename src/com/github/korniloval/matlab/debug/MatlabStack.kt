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
                val size1 = matchResult.groups["size1"]?.value?.toInt()
                val size2 = matchResult.groups["size2"]?.value?.toInt()
                var type = matchResult.groups["class"]?.value
                if (type != null && size1 != null && size2 != null) {
                    if (size1 == 1 && size2 > 1) type = "vector<$type>"
                    else if (size1 > 1 && size2 > 1) type = "matrix<$type>"
                }
                matchResult.groups["name"]?.value?.let { children.add(MatlabValue(it, type, debugProcess)) }
            }

            override fun end() {
                node.addChildren(children, true)
            }
        })
    }

    companion object {
        private val VAR_DESC_PATTERN = Regex("\\s+(?<attr>\\w+)?\\s+(?<name>\\w+)\\s+(?<size1>\\d)+x(?<size2>\\d+)\\s+(?<bytes>\\d+)\\s+(?<class>\\w+)\n")
        private const val AUTO_VAR_ATTR = 'a'
    }
}

class MatlabValue(name: String, private val type: String?, private val debugProcess: MatlabDebugProcess) : XNamedValue(name) {
    override fun computePresentation(node: XValueNode, place: XValuePlace) {
        debugProcess.commandsQueue.queue.add(object : Command(name) {
            private val sb = StringBuilder()
            private val prefix = Regex("^$name =\\s+")
            private var first = true

            override fun process(text: String) {
                if (first) {
                    first = false
                    val res = prefix.find(text)
                    if (res != null) sb.append(text.substring(res.value.length))
                    else sb.append(text)
                }
                else sb.append(text)
            }

            override fun end() {
                node.setPresentation(AllIcons.Nodes.Variable, type, sb.toString().trim(), false)
            }
        })
    }
}
