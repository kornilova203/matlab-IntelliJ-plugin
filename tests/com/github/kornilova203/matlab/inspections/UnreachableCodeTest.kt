package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("inspections/unreachablecode")
class UnreachableCodeTest : BasePlatformTestCase(){
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun testIf() = doTest()
    fun testLoop() = doTest()
    fun testReturn() = doTest()

    private fun doTest() {
        myFixture.configureByFile(getTestName(false) + ".m")
        myFixture.enableInspections(MatlabUnreachableCodeInspection::class.java)
        myFixture.checkHighlighting()
    }
}