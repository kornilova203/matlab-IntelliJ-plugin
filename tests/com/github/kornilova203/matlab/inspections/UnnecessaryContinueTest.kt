package com.github.kornilova203.matlab.inspections

import com.intellij.testFramework.TestDataPath

@TestDataPath("inspections/unnecessarycontinue")
class UnnecessaryContinueTest : MatlabInspectionTest() {
    fun testWhile() = doTest(true)
    fun testFor() = doTest(true)
    fun testOutsideLoop() = doTest(false)
    fun testNecessaryContinue() = doTest(false)
    fun testSingleStatementInBlock() = doTest(true)
    
    fun doTest(checkQuickFix: Boolean) {
        super.doTest(checkQuickFix, MatlabUnnecessaryContinueInspection::class.java)
    }
}