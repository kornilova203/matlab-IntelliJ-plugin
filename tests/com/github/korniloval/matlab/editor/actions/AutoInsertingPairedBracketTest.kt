package com.github.korniloval.matlab.editor.actions

import com.intellij.openapi.editor.VisualPosition
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class AutoInsertingPairedBracketTest : BasePlatformTestCase() {

    fun testSimpleCases() {
        myFixture.configureByText("test.m", "")

        myFixture.type("(")

        assertEquals("""
                     ()
                     """.trimIndent(), getText())
        assertEquals(1, getOffset())

        myFixture.type("(")

        assertEquals("""
                     (())
                     """.trimIndent(), getText())
        assertEquals(2, getOffset())

        myFixture.type("{")

        assertEquals("""
                     (({}))
                     """.trimIndent(), getText())
        assertEquals(3, getOffset())

        myFixture.editor.caretModel.moveToVisualPosition(VisualPosition(0, 4))

        myFixture.type("(")
        assertEquals("""
                     (({}()))
                     """.trimIndent(), getText())
        assertEquals(5, getOffset())
    }

    fun testCasesWhenInsertNonNeeded() {

        myFixture.configureByText("test.m", "")

        myFixture.type(")")

        assertEquals(")", getText())
        assertEquals(1, getOffset())

        myFixture.editor.caretModel.moveToVisualPosition(VisualPosition(0, 0))

        myFixture.type("(")

        assertEquals("()", getText())
        assertEquals(1, getOffset())
    }

    private fun getOffset(): Int = myFixture.editor.caretModel.offset

    private fun getText(): String = myFixture.editor.document.charsSequence.toString()
}
