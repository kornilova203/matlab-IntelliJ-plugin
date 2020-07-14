package com.github.kornilova203.matlab.completion

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("completion")
class CompletionTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun testF() = doTest("function", "for", "classdef", "if")
    fun testIf() = doTest("function", "for", "classdef", "if", "end", "while")
    fun testWhileCondition() = doTest()
    fun testVarInSameScope() = doTest("var1", "var2")
    fun testVarInDifferentScopes() = doTest("var1", "var2")
    fun testShadowingVar() = doTest("var1", "var2")
    fun testNumber() = doTest()
    fun testNumberDot() = doTest()

    private fun doTest(vararg completionVariants: String) {
        myFixture.testCompletionVariants(getTestFilePath(), *completionVariants)
    }

    private fun getTestFilePath(): String = testDataPath + "/" + getTestName(false) + ".m"
}
