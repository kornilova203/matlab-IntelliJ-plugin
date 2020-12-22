package com.github.kornilova203.matlab.highlighting

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.openapi.util.text.StringUtil
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

        myFixture.configureByText(testFile.name, StringUtil.convertLineSeparators(testFile.readText()))

        myFixture.testHighlighting()
    }
}
