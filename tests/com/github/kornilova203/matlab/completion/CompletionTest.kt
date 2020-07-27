package com.github.kornilova203.matlab.completion

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.UsefulTestCase
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
    fun testPackage() = doTest("apputil", "engine", "exception", "lang", "system", "types")

    private fun doTest(vararg completionVariants: String) {
        myFixture.configureByFile(getTestFilePath())
        myFixture.complete(CompletionType.BASIC)
        val variants = myFixture.lookupElementStrings
        UsefulTestCase.assertContainsElements(variants!!, *completionVariants)
    }

    private fun getTestFilePath(): String = testDataPath + "/" + getTestName(false) + ".m"
}
