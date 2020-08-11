package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.psi.*
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiFile

class MatlabUnnecessaryReturnInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean) =
            object : MatlabUnnecessaryControlExprVisitor(holder, MatlabTypes.RETURN, "return") {
                override fun visitFile(file: PsiFile) {
                    findLastReturn(file)
                }

                override fun visitFunctionDeclaration(node: MatlabFunctionDeclaration) {
                    findLastReturn(node.block)
                }
            }
}