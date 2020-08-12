package com.github.kornilova203.matlab.editor.actions

import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class EnterInStringLiteralTest : BasePlatformTestCase()  {
    fun testSingleQuote() = doTest("\'")
    fun testDoubleQuote() = doTest("\"")

    private fun doTest(quote: String) {
        myFixture.configureByText("test.m", "a = " + quote + "hello<caret>world" + quote)
        myFixture.performEditorAction(IdeActions.ACTION_EDITOR_ENTER)
        myFixture.checkResult("a = strcat(" + quote + "hello" + quote + ",\n        " + quote + "world" + quote + ")")
    }
}