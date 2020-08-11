package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("inspections/unnecessarycontinue")
class UnnecessaryContinueTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun testWhile() = doTest(true)
    fun testFor() = doTest(true)
    fun testOutsideLoop() = doTest(false)
    fun testNecessaryContinue() = doTest(false)

    private fun doTest(checkQuickFix: Boolean) {
        myFixture.configureByFile(getTestName(false) + ".m")
        myFixture.enableInspections(MatlabUnnecessaryContinueInspection::class.java)
        myFixture.checkHighlighting()
        if (checkQuickFix) {
            val action = myFixture.findSingleIntention("Remove")
            myFixture.launchAction(action)
            myFixture.checkResultByFile(getTestName(false) + "_after.m")
        }
    }
}