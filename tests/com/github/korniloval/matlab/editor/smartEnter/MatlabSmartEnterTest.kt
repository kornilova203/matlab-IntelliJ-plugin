package com.github.korniloval.matlab.editor.smartEnter

import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.openapi.editor.VisualPosition
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase

class MatlabSmartEnterTest : BasePlatformTestCase() {

  fun testSmartEnterShouldCompleteAssignmentStatement() {
    myFixture.configureByText("test.m", "foo = 'bar'")

    myFixture.editor.caretModel.moveToVisualPosition(VisualPosition(0, 10))

    myFixture.performEditorAction(IdeActions.ACTION_EDITOR_COMPLETE_STATEMENT)

    assertEquals("""
                     foo = 'bar';
                     """.trimIndent(), getText())

    assertEquals(12, getOffset())
  }

  fun testSmartEnterShouldNotCompleteACompletedAssignmentStatement() {
    myFixture.configureByText("test.m", "foo = 'bar';")

    myFixture.editor.caretModel.moveToVisualPosition(VisualPosition(0, 10))

    myFixture.performEditorAction(IdeActions.ACTION_EDITOR_COMPLETE_STATEMENT)

    assertEquals("""
                     foo = 'bar';

                     """.trimIndent(), getText())

    assertEquals(13, getOffset())
  }

  fun testSmartEnterShouldCompleteMultiLineAssignmentStatement() {
    myFixture.configureByText("test.m", """
      foo = ...
        'bar'
    """.trimIndent())

    myFixture.editor.caretModel.moveToVisualPosition(VisualPosition(0, 8)) // on the dots

    myFixture.performEditorAction(IdeActions.ACTION_EDITOR_COMPLETE_STATEMENT)

    assertEquals("""
      foo = ...
        'bar';
    """.trimIndent(), getText())

    assertEquals(18, getOffset())
  }

  fun testSmartEnterShouldCompleteFunctionStatement() {
    myFixture.configureByText("test.m", """print("bestTestEver")""")

    myFixture.editor.caretModel.moveToVisualPosition(VisualPosition(0, 5))

    myFixture.performEditorAction(IdeActions.ACTION_EDITOR_COMPLETE_STATEMENT)

    assertEquals("""print("bestTestEver");""", getText())

    assertEquals(22, getOffset())
  }

  fun testSmartEnterShouldNotCompleteACompletedFunctionStatement() {
    myFixture.configureByText("test.m", """print("bestTestEver");""")

    myFixture.editor.caretModel.moveToVisualPosition(VisualPosition(0, 5))

    myFixture.performEditorAction(IdeActions.ACTION_EDITOR_COMPLETE_STATEMENT)

    assertEquals("""print("bestTestEver");
      |
    """.trimMargin(), getText())

    assertEquals(23, getOffset())
  }


  private fun getOffset(): Int = myFixture.editor.caretModel.offset

  private fun getText(): String = myFixture.editor.document.charsSequence.toString()
}
