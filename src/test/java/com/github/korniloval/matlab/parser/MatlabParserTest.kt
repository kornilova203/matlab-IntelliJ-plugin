package com.github.korniloval.matlab.parser

import com.github.korniloval.matlab.MatlabParserDefinition
import com.intellij.testFramework.ParsingTestCase

open class MatlabParserTest : ParsingTestCase("", "m", MatlabParserDefinition()) {

    override fun getTestDataPath(): String = "src/test/resources/parser"

    fun testNumbers() = doTest(true)
    fun testFileOperations() = doTest(true)
    fun testWhileLoop() = doTest(true)
    fun testForLoop() = doTest(true)
    fun testIf() = doTest(true)
    fun testFunctionCall() = doTest(true)
    fun testFunctionDeclaration() = doTest(true)
    fun testClassDeclaration() = doTest(true)
    fun testPropertyAccess() = doTest(true)
    fun testStrings() = doTest(true)
    fun testOneSingleQuote() = doTest(true)
    fun testComments() = doTest(true)
    fun testCellArray() = doTest(true)
    fun testMatrix() = doTest(true)
    fun testLambda() = doTest(true)
    fun testSwitch() = doTest(true)
}