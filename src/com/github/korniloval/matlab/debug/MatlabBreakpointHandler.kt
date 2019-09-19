package com.github.korniloval.matlab.debug

import com.intellij.xdebugger.breakpoints.XBreakpointHandler
import com.intellij.xdebugger.breakpoints.XLineBreakpoint

typealias MatlabXLineBreakpoint = XLineBreakpoint<MatlabLineBreakpointProperties>

class MatlabBreakpointHandler : XBreakpointHandler<MatlabXLineBreakpoint>(MatlabLineBreakpointType::class.java) {
    val breakpoints = mutableSetOf<MatlabXLineBreakpoint>()

    override fun registerBreakpoint(breakpoint: MatlabXLineBreakpoint) {
        breakpoints.add(breakpoint)
    }

    override fun unregisterBreakpoint(breakpoint: MatlabXLineBreakpoint, temporary: Boolean) {
        breakpoints.remove(breakpoint)
    }
}
