package com.github.korniloval.matlab.completion

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase

class CompletionTest : LightPlatformCodeInsightFixtureTestCase() {
    override fun getTestDataPath(): String = "testData/completion"

    fun testF() = doTest("function", "for", "classdef", "if")
    fun testIf() = doTest("function", "for", "classdef", "if", "end", "while")
    fun testWhileCondition() = doTest()

    private fun doTest(vararg completionVariants: String) {
        myFixture.testCompletionVariants(getTestFilePath(), *completionVariants)
    }

    private fun getTestFilePath(): String = testDataPath + "/" + getTestName(false) + ".m"
}
