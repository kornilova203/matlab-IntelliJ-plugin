package com.github.korniloval.matlab.debug

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.xdebugger.breakpoints.XLineBreakpointType

class MatlabLineBreakpointType : XLineBreakpointType<MatlabLineBreakpointProperties>(MatlabLineBreakpointType::class.java.simpleName, "Matlab line breakpoint") {
    override fun createBreakpointProperties(file: VirtualFile, line: Int) = MatlabLineBreakpointProperties()

    override fun canPutAt(file: VirtualFile, line: Int, project: Project): Boolean {
        return true
    }
}
