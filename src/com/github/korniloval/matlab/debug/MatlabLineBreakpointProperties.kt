package com.github.korniloval.matlab.debug

import com.intellij.xdebugger.breakpoints.XBreakpointProperties

class MatlabLineBreakpointProperties : XBreakpointProperties<MatlabLineBreakpointIdentity>() {
    override fun getState() = MatlabLineBreakpointIdentity()

    override fun loadState(state: MatlabLineBreakpointIdentity) {}
}

class MatlabLineBreakpointIdentity
