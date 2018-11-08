package com.github.korniloval.matlab.refactoring.rename

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase

/**
 * @author Liudmila Kornilova
 **/
class RenameTest : LightPlatformCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String = "testData/rename"

    fun testVar() = doTest()
    fun testFunction() = doTest()

    fun doTest() {
        val testName = getTestName(false)
        myFixture.testRename("$testName.m", "$testName-expected.m", "afterRename")
    }
}
