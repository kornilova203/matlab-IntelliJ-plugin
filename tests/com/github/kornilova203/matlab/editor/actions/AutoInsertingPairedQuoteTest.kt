package com.github.kornilova203.matlab.editor.actions

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class AutoInsertingPairedQuoteTest : BasePlatformTestCase() {

    fun testSingleQuote() {
        myFixture.configureByText("test.m", "")

        myFixture.type("'")

        assertEquals("''", getText())
        assertEquals(1, getOffset())

        myFixture.type("\"")

        assertEquals("""
                     '"'
                     """.trimIndent(), getText())
        assertEquals(2, getOffset())

        myFixture.type("'")

        assertEquals("""
                     '"'
                     """.trimIndent(), getText())
        assertEquals(3, getOffset())

        myFixture.type("'")

        assertEquals("""
                     '"''
                     """.trimIndent(), getText())
        assertEquals(4, getOffset())
    }

    fun testDoubleQuote() {
        myFixture.configureByText("test.m", "")

        myFixture.type("\"")

        assertEquals("""
                     ""
                     """.trimIndent(),
                getText())
        assertEquals(1, getOffset())

        myFixture.type("\"")

        assertEquals("""
                     ""
                     """.trimIndent(),
                getText())
        assertEquals(2, getOffset())

        myFixture.type("\"")

        assertEquals("\"\"\"\"",
                getText())
        assertEquals(3, getOffset())

        myFixture.type("\\")
        myFixture.type("\"")

        assertEquals("\"\"\"\\\"\"",
                getText())
        assertEquals(5, getOffset())
    }

    private fun getOffset(): Int = myFixture.editor.caretModel.offset

    private fun getText(): String = myFixture.editor.document.charsSequence.toString()
}
