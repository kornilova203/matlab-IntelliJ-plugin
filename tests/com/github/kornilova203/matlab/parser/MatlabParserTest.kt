package com.github.kornilova203.matlab.parser

import com.github.kornilova203.matlab.MatlabParserDefinition
import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.testFramework.ParsingTestCase
import com.intellij.testFramework.TestDataPath

@TestDataPath("parser")
open class MatlabParserTest : ParsingTestCase("", "m", MatlabParserDefinition()) {

    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun doTest() = doTest(true)

    fun testNumbers() = doTest()
    fun testFileOperations() = doTest()
    fun testWhileLoop() = doTest()
    fun testForLoop() = doTest()
    fun testGlobalVariables() = doTest()
    fun testIf() = doTest()
    fun testFunctionCall() = doTest()
    fun testFunctionDeclaration() = doTest()
    fun testClassDeclaration() = doTest()
    fun testPropertyAccess() = doTest()
    fun testStrings() = doTest()
    fun testOneSingleQuote() = doTest()
    fun testSingleQuote() = doTest()
    fun testComments() = doTest()
    fun testCellArray() = doTest()
    fun testMatrix() = doTest()
    fun testLambda() = doTest()
    fun testSwitch() = doTest()
    fun testExpressions() = doTest()
    fun testStructs() = doTest()
    fun testTryCatch() = doTest()
    fun testEnumeration() = doTest()
}