package com.github.kornilova203.matlab.resolve

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

/**
 * @author Liudmila Kornilova
 **/
@TestDataPath("findusages")
class FindUsagesTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun testVariable() = doTest(2)
    fun testFunction() = doTest(1)

    private fun doTest(usagesCount: Int) {
        val usages = myFixture.testFindUsages(getTestName(false) + ".m")
        assertEquals(usagesCount, usages.size)
    }
}
