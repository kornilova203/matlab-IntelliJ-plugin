package com.github.korniloval.matlab.completion

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase

class CompletionTest : LightPlatformCodeInsightFixtureTestCase() {
    override fun getTestDataPath(): String = "src/test/resources/completion"

    fun testF() = doTest("function", "elseif", "for", "classdef", "if")

    private fun doTest(vararg completionVariants: String) {
        myFixture.testCompletionVariants(getTestFilePath(), *completionVariants)
    }

    private fun getTestFilePath(): String = testDataPath + "/" + getTestName(true) + ".m"
}
