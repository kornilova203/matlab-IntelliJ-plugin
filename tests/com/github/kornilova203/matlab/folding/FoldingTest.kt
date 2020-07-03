package com.github.kornilova203.matlab.folding

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("folding")
class FoldingTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    private fun doTest() {
        myFixture.testFolding(testDataPath + "/" + getTestName(false) + ".m")
    }

    fun testIf() = doTest()
    fun testIfElse() = doTest()
    fun testFor() = doTest()
    fun testSwitch() = doTest()
    fun testFunction() = doTest()
    fun testClass() = doTest()
}