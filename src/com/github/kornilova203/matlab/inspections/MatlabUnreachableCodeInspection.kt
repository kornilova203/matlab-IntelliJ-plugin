package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.MatlabControlFlowBuilder
import com.github.kornilova203.matlab.psi.MatlabFunctionDeclaration
import com.github.kornilova203.matlab.psi.MatlabVisitor
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.parents
import kotlin.collections.HashSet

class MatlabUnreachableCodeInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean) =
            object : MatlabVisitor() {
                val unreachable: MutableSet<PsiElement?> = HashSet()
                override fun visitFile(file: PsiFile) {
                    val controlFlow = MatlabControlFlowBuilder().buildControlFlow(file) ?: return
                    for (instruction in controlFlow.instructions) {
                        // First instruction is entry, it's always null and it shouldn't be checked
                        // Second instruction is first command, so it will always be executed and it shouldn't be checked too
                        if (instruction.num() < 2 || instruction.element is MatlabFunctionDeclaration) {
                            continue
                        }
                        if (instruction.allPred().all {unreachable.contains(it.element)}) {
                            unreachable.add(instruction.element)
                        }
                    }
                    for (element in unreachable) {
                        if (element == null) {
                            continue
                        }
                        if (!element.parents.any {unreachable.contains(it)}) {
                            holder.registerProblem(element, "Unreachable code")
                        }
                    }
                }
            }
}