package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("inspections/unusedvariable")
class UnusedVariableTest : MatlabInspectionTest() {
    fun testVar() = doTest()
    fun testVarInFunction() = doTest()
    fun testRemoveSemicolon() = doTest()
    fun testParameter() = doTest()
    fun testFirstParameter() = doTest()
    fun testMiddleParameter() = doTest()
    fun testLastParameter() = doTest()
    fun testException() = doTest()
    fun testRetValue() = doTest()
    fun testNotHighlightRetValue() = doTest()
    fun testSkipQualified() = doTest()

    private fun doTest() {
        super.doTest(true, MatlabUnusedVariableInspection::class.java)
    }
}