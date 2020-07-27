package com.github.kornilova203.matlab.lineindent

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.io.File

@TestDataPath("lineindent")
class LineIndentTest : BasePlatformTestCase() {
    private val keywords : Array<String> = arrayOf("end", "else", "elseif", "case", "otherwise", "catch")

    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun testIf() = doTest()
    fun testIfNested() = doTest()
    fun testTryCatch() = doTest()
    fun testSwitch() = doTest()
    fun testClass() = doTest()
    fun testNotIndent() {
        myFixture.configureByText("test.m", "")
        myFixture.type("if a > b a end")
        myFixture.performEditorAction(IdeActions.ACTION_EDITOR_ENTER)
        myFixture.checkResult("if a > b a end\n")
    }

    private fun doTest() {
        myFixture.configureByText("test.m", "")
        val file = File(testDataPath, getTestName(false) + ".m")
        file.forEachLine { line ->
            myFixture.type(line)
            findKeyword(line)
            myFixture.performEditorAction(IdeActions.ACTION_EDITOR_ENTER)
        }
        myFixture.checkResultByFile(getTestName(false) + "_after.m")
    }

    private fun findKeyword(line : String) {
        for (keyword in keywords) {
            val startKeyword = line.indexOf(keyword)
            if (startKeyword > -1) {
                repeat(line.length - startKeyword + 1) {
                    myFixture.performEditorAction(IdeActions.ACTION_EDITOR_MOVE_CARET_LEFT)
                }
                myFixture.performEditorAction(IdeActions.ACTION_EDITOR_ENTER)
                repeat(line.length - startKeyword) {
                    myFixture.performEditorAction(IdeActions.ACTION_EDITOR_MOVE_CARET_RIGHT)
                }
                findKeyword(line.substringAfter(keyword))
                break
            }
        }
    }
}