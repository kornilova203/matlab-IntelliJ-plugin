package com.github.korniloval.matlab.editor.actions

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import org.jetbrains.annotations.NotNull
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase


class CommenterLineCommentTest : LightPlatformCodeInsightFixtureTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/resources/editor/commenter/"
    }

    @NotNull
    private fun doTest(actionId:String){
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