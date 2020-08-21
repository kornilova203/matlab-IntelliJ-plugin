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
    fun testMatrixElement() = doTest(false)
    fun testMatrix() = doTest()
    fun testSameRetValueAndParameter() = doTest(false)
    fun testSameRetValueAndParameterUnused() = doTest()
    fun testFor() = doTest(false)

    private fun doTest(checkFix: Boolean = true) {
        super.doTest(checkFix, MatlabUnusedVariableInspection::class.java)
    }
}