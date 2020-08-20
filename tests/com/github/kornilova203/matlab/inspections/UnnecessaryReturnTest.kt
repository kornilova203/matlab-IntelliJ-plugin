package com.github.kornilova203.matlab.inspections

import com.intellij.testFramework.TestDataPath

@TestDataPath("inspections/unnecessaryreturn")
class UnnecessaryReturnTest : MatlabInspectionTest() {
    fun testFunction() = doTest()
    fun testFile() = doTest()

    fun doTest() {
        super.doTest(true, MatlabUnnecessaryReturnInspection::class.java)
    }
}