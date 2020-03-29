package com.github.kornilova203.matlab.refactoring.rename

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

/**
 * @author Liudmila Kornilova
 **/
@TestDataPath("rename")
class RenameTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun testVar() = doTest()
    fun testFunction() = doTest()

    fun doTest() {
        val testName = getTestName(false)
        myFixture.testRename("$testName.m", "$testName-expected.m", "afterRename")
    }
}
