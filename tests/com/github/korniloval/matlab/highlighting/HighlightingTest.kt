package com.github.korniloval.matlab.highlighting

import com.github.korniloval.matlab.getTestDataRoot
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.io.File

@TestDataPath("highlighting")
class HighlightingTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun testFunctionCall() = doTest()
    fun testFunctionDefinition() = doTest()

    private fun doTest() {
        val testFile = File(testDataPath, getTestName(true) + ".m")

        myFixture.configureByText(testFile.name, testFile.readText())

        myFixture.testHighlighting()
    }
}
