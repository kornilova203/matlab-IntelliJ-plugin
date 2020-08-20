package com.github.kornilova203.matlab.editor.actions

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("editor/actions/typed")
class TypedTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)
    
    fun testEnd() = doTest()
    fun testCatch() = doTest()
    fun testCase() = doTest()
    
    private fun doTest() {
        myFixture.configureByFile(getTestName(false) + ".m")
        myFixture.type(' ')
        myFixture.checkResultByFile(getTestName(false) + "_after.m")
    }
}