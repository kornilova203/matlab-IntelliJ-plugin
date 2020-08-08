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
                        if (instruction.num() < 2 || instruction.element is MatlabFunctionDeclaration) {
                            continue
                        }
                        if (instruction.allPred().isEmpty()) {
                            unreachable.add(instruction.element)
                        } else {
                            var allPredUnreachable = true
                            for (pred in instruction.allPred()) {
                                if (!unreachable.contains(pred.element)) {
                                    allPredUnreachable = false
                                    break
                                }
                            }
                            if (allPredUnreachable) {
                                unreachable.add(instruction.element)
                            }
                        }
                    }
                    for (element in unreachable) {
                        if (element == null) {
                            continue
                        }
                        var isParentUnreachable = false
                        for (parent in element.parents) {
                            if (unreachable.contains(parent)) {
                                isParentUnreachable = true
                                break
                            }
                        }
                        if (!isParentUnreachable) {
                            holder.registerProblem(element, "Unreachable code")
                        }
                    }
                }
            }
}