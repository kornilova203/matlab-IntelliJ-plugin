package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("inspections/unnecessaryreturn")
class UnnecessaryReturnTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun testFunction() = doTest(true)
    fun testFile() = doTest(true)

    private fun doTest(checkQuickFix: Boolean) {
        myFixture.configureByFile(getTestName(false) + ".m")
        myFixture.enableInspections(MatlabUnnecessaryReturnInspection::class.java)
        myFixture.checkHighlighting()
        if (checkQuickFix) {
            val action = myFixture.findSingleIntention("Remove")
            myFixture.launchAction(action)
            myFixture.checkResultByFile(getTestName(false) + "_after.m")
        }
    }
}