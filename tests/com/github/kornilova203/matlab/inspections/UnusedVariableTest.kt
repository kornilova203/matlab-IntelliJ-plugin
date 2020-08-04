package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("inspections/unusedvariable")
class UnusedVariableTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun testVar() = doTest()
    fun testVarInFunction() = doTest()
    fun testRemoveSemicolon() = doTest()
    fun testParameter() = doTest()
    fun testFirstParameter() = doTest()
    fun testMiddleParameter() = doTest()
    fun testLastParameter() = doTest()
    fun testException() = doTest()


    private fun doTest() {
        myFixture.configureByFile(getTestName(false) + ".m")
        myFixture.enableInspections(MatlabUnusedVariableInspection::class.java)
        myFixture.checkHighlighting()
        val action = myFixture.findSingleIntention("Remove")
        myFixture.launchAction(action)
        myFixture.checkResultByFile(getTestName(false) + "_after.m")

    }
}