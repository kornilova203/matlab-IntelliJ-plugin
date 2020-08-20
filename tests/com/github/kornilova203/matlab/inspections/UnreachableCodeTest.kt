package com.github.kornilova203.matlab.inspections

import com.intellij.testFramework.TestDataPath

@TestDataPath("inspections/unreachablecode")
class UnreachableCodeTest : MatlabInspectionTest() {
    fun testIf() = doTest()
    fun testLoop() = doTest()
    fun testReturn() = doTest()

    private fun doTest() {
        super.doTest(false, MatlabUnreachableCodeInspection::class.java)
    }
}