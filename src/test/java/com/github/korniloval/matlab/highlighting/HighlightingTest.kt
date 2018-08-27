package com.github.korniloval.matlab.highlighting

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase
import java.io.File

class HighlightingTest : LightPlatformCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String = "src/test/resources/highlighting"

    fun testFunctionCall() = doTest()
    fun testFunctionDefinition() = doTest()

    private fun doTest() {
        val testFile = File(testDataPath, getTestName(true) + ".m")

        myFixture.configureByText(testFile.name, testFile.readText())

        myFixture.testHighlighting()
    }
}
