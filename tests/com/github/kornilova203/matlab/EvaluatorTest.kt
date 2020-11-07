package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.MatlabExpr
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase


class EvaluatorTest : BasePlatformTestCase() {
    fun testRef() = doTest("true", true)
    fun testFunRef() = doTest("false()", false)
    fun testNumber() = doTest("1", true)
    fun testZero() = doTest("0", false)
    fun testZeroDouble() = doTest("0.0", false)
    fun testString() = doTest("\"a\"", true)
    fun testEmptyString() = doTest("\'\'", false)
    fun testCompare() = doTest("0 < 1", true)
    fun testArithmetic() = doTest("-4 + (2 * 2)", false)
    fun testLogical() = doTest("~(0 < 1 || 0 > 1 && 0 > 1)", false)

    private fun doTest(text: String, expected: Boolean) {
        val file = myFixture.configureByText("test.m", text)
        UsefulTestCase.assertInstanceOf(file.firstChild, MatlabExpr::class.java)
        val actual = evaluateAsBoolean(file.firstChild as? MatlabExpr)
        TestCase.assertEquals(expected, actual)
    }
}