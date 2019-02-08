package com.github.korniloval.matlab.resolve

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase

/**
 * @author Liudmila Kornilova
 **/
class FindUsagesTest : LightPlatformCodeInsightFixtureTestCase() {
    override fun getTestDataPath(): String = "testData/findusages/"

    fun testVariable() = doTest(2)
    fun testFunction() = doTest(1)

    private fun doTest(usagesCount: Int) {
        val usages = myFixture.testFindUsages(getTestName(false) + ".m")
        assertEquals(usagesCount, usages.size)
    }
}
