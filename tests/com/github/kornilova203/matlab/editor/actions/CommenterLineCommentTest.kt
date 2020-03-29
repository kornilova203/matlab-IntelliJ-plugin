package com.github.kornilova203.matlab.editor.actions

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.jetbrains.annotations.NotNull


@TestDataPath("commenter")
class CommenterLineCommentTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    @NotNull
    private fun doTest(actionId: String) {
        myFixture.configureByFile(getTestName(false) + ".m")
        myFixture.performEditorAction(actionId)
        myFixture.checkResultByFile(getTestName(false) + "_after.m", true)
    }

    fun testLineComment() {
        doTest(IdeActions.ACTION_COMMENT_LINE)
    }

    fun testRemoveLineComment() {
        doTest(IdeActions.ACTION_COMMENT_LINE)
    }

    fun testBlockComment() {
        doTest(IdeActions.ACTION_COMMENT_BLOCK)
    }

    fun testRemoveBlockComment() {
        doTest(IdeActions.ACTION_COMMENT_BLOCK)
    }
}