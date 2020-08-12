package com.github.kornilova203.matlab.controlflow

import com.github.kornilova203.matlab.MatlabControlFlowBuilder
import com.github.kornilova203.matlab.getTestDataRoot
import com.github.kornilova203.matlab.psi.MatlabExpr
import com.intellij.codeInsight.controlflow.ConditionalInstruction
import com.intellij.codeInsight.controlflow.ControlFlow
import com.intellij.codeInsight.controlflow.Instruction
import com.intellij.psi.util.elementType
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import java.io.File
import java.lang.StringBuilder

@TestDataPath("controlflow")
class ControlFlowTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun testSimple() = doTest()
    fun testIf() = doTest()
    fun testElse() = doTest()
    fun testElseIf() = doTest()
    fun testNestedIf() = doTest()
    fun testSwitch() = doTest()
    fun testWhile() = doTest()
    fun testFor() = doTest()
    fun testTryCatch() = doTest()
    fun testFunction() = doTest()
    fun testControlExpr() = doTest()

    private fun doTest() {
        val file = myFixture.configureByFile(getTestName(false) + ".m")
        val controlFlow = MatlabControlFlowBuilder().buildControlFlow(file)
        val actual = printControlFlow(controlFlow!!)
        val expectedFile = File(testDataPath + "/" + getTestName(false) + ".txt")
        val expected = expectedFile.readText()
        TestCase.assertEquals(expected, actual)
        val actualGraph = drawGraph(controlFlow)
        val expectedGraph = File(testDataPath + "/" + getTestName(false) + "_graph.txt").readText()
        TestCase.assertEquals(expectedGraph, actualGraph)
    }
    
    private fun printControlFlow(controlFlow: ControlFlow): String {
        val result = StringBuilder()
        for (instruction in controlFlow.instructions) {
            result.append(instruction, "\n")
        }
        return result.toString()
    }

    private fun drawGraph(controlFlow: ControlFlow): String {
        var maxLength = 0
        var balance = 0
        var maxBalance = 0
        for (instruction in controlFlow.instructions) {
            val text = printInstruction(instruction)
            val length = text.length
            if (length > maxLength) {
                maxLength = length
            }

            for (pred in instruction.allPred()) {
                if (pred.num() > instruction.num()) {
                    balance++
                }
            }
            if (balance > maxBalance) {
                maxBalance = balance
            }
            for (succ in instruction.allSucc()) {
                if (succ.num() < instruction.num()) {
                    balance--
                }
            }
        }

        val offset = if (maxBalance == 0) 0 else maxBalance * 3 + DrawableControlFlow.MAGIC_INDENT
        val indent = " ".repeat(offset)
        val lines = listOf<String>().toMutableList()
        for (instruction in controlFlow.instructions) {
            val text = printInstruction(instruction)
            lines.add(indent + text)
            lines.add(indent)
            lines.add(indent)
        }
        return DrawableControlFlow(controlFlow, lines, maxLength + offset, offset).draw()

    }

    class DrawableControlFlow(private val controlFlow: ControlFlow,
                              private val lines: MutableList<String>,
                              private val maxLength: Int,
                              private val offset: Int) {
        companion object {
            val freeDownEdges = ArrayList<Int>().toMutableList()
            val freeUpEdges = ArrayList<Int>().toMutableList()
            const val MAGIC_INDENT = 6
        }

        fun draw(): String {
            for (instruction in controlFlow.instructions) {
                for (succ in instruction.allSucc()) {
                    if (succ.num() < instruction.num()) {
                        freeUpEdge(succ.num())
                    } else {
                        addEdge(lines, instruction.num(), succ.num())
                    }
                }
                for (pred in instruction.allPred()) {
                    if (pred.num() + 1 < instruction.num()) {
                        freeDownEdge(pred.num())
                    }
                    if (instruction.num() < pred.num()) {
                        addEdge(lines, pred.num(), instruction.num())
                    }
                }
            }
            return lines.subList(0, lines.size - 2).joinToString("\n")
        }

        private fun addEdge(lines: MutableList<String>, start: Int, end: Int) {
            if (start == end) return
            val startLine = start * 3
            val endLine = end * 3
            when {
                end - 1 == start -> {
                    lines[startLine + 1] = addSymbol(lines[startLine + 1], '|', offset)
                    lines[startLine + 2] = addSymbol(lines[startLine + 2], 'v', offset)
                }
                start < end -> {
                    val freeEdgeNum = findFreeDownEdge(start) + 1
                    val indexEdge = maxLength - 1 + freeEdgeNum * 3
                    lines[startLine] = addOutDownEdge(lines[startLine], indexEdge, start)
                    for (i in startLine + 1 until endLine) {
                        lines[i] = addSymbol(lines[i], '|', indexEdge)
                    }
                    lines[endLine] = addInDownEdge(lines[endLine], indexEdge, end)
                }
                end < start -> {
                    val freeEdgeNum = findFreeUpEdge(end) + 1
                    val indexEdge = offset - freeEdgeNum * 3 - MAGIC_INDENT
                    lines[startLine] = addOutUpEdge(lines[startLine], indexEdge)
                    for (i in endLine + 1 until startLine) {
                        lines[i] = addSymbol(lines[i], '|', indexEdge)
                    }
                    lines[endLine] = addInUpEdge(lines[endLine], indexEdge)
                }
            }
        }

        private fun findFreeDownEdge(num: Int): Int {
            return findFreeEdge(num, freeDownEdges)
        }

        private fun findFreeUpEdge(num: Int): Int {
            return findFreeEdge(num, freeUpEdges)
        }

        private fun findFreeEdge(num: Int, freeEdges: MutableList<Int>): Int {
            for (i in 0 until freeEdges.size) {
                if (freeEdges[i] == 0) {
                    freeEdges[i] = num
                    return i
                }
            }
            freeEdges.add(num)
            return freeEdges.size - 1
        }

        private fun freeDownEdge(num: Int) {
            freeEdge(num, freeDownEdges)
        }

        private fun freeUpEdge(num: Int) {
            freeEdge(num, freeUpEdges)
        }

        private fun freeEdge(num: Int, freeEdges: MutableList<Int>) {
            for (i in 0 until freeEdges.size) {
                if (freeEdges[i] == num) {
                    freeEdges[i] = 0
                }
            }
        }

        private fun addOutDownEdge(line: String, indexEdge: Int, numInstruction: Int): String {
            val originalLength = originalLength(numInstruction)
            if (line.length < indexEdge) {
                if (line.length == originalLength) {
                    return line + "-".repeat(indexEdge - line.length) + "+"
                }
                return line.substring(0, originalLength) + line.substring(originalLength, line.length).replace(' ', '-') + "-".repeat(indexEdge - line.length) + "+"
            }
            return line.substring(0, originalLength) + line.substring(originalLength, indexEdge).replace(' ', '-') + "+" + line.substring(indexEdge + 1)
        }

        private fun addInDownEdge(line: String, indexEdge: Int, numInstruction: Int): String {
            val originalLength = originalLength(numInstruction)
            if (line.length < indexEdge) {
                if (line.length == originalLength) {
                    return line + "<" + "-".repeat(indexEdge - line.length - 1) + "+"
                }
                return line.substring(0, originalLength) + "<" + line.substring(originalLength + 1, line.length).replace(' ', '-') + "-".repeat(indexEdge - line.length) + "+"
            }
            return line.substring(0, originalLength) + "<" + line.substring(originalLength + 1, indexEdge).replace(' ', '-') + "+" + line.substring(indexEdge + 1)
        }

        private fun addOutUpEdge(line: String, indexEdge: Int): String {
            return line.substring(0, indexEdge) + "+" + line.substring(indexEdge + 1, offset).replace(' ', '-') + line.substring(offset)
        }

        private fun addInUpEdge(line: String, indexEdge: Int): String {
            return line.substring(0, indexEdge) + "+" + line.substring(indexEdge + 1, offset - 1).replace(' ', '-') + ">" + line.substring(offset)
        }

        private fun originalLength(numInstruction: Int): Int {
            return printInstruction(controlFlow.instructions[numInstruction]).length + offset
        }

        private fun addSymbol(line: String, symbol: Char, index: Int): String {
            if (line.length <= index) {
                return line + " ".repeat(index - line.length) + symbol
            }
            return line.substring(0, index) + symbol + line.substring(index + 1, line.length)
        }
    }
}

fun printInstruction(instruction: Instruction): String {
    val element = instruction.element ?: return "null"
    return when {
        element is MatlabExpr -> element.elementType.toString() + ": " + element.text
        instruction is ConditionalInstruction -> element.elementType.toString() + " - if " + instruction.condition.text + " is " + instruction.result
        else -> element.elementType.toString()
    }
}

