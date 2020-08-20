package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.psi.*
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder

class MatlabUnnecessaryContinueInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean) =
            object : MatlabUnnecessaryControlExprVisitor(holder, MatlabTypes.CONTINUE, "continue") {
                override fun visitWhileLoop(node: MatlabWhileLoop) {
                    findUnnecessaryExpr(node.block)
                }

                override fun visitForLoop(node: MatlabForLoop) {
                    findUnnecessaryExpr(node.block)
                }
            }
}