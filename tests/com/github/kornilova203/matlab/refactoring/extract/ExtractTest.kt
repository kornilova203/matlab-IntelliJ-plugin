package com.github.kornilova203.matlab.refactoring.extract

import com.github.kornilova203.matlab.getTestDataRoot
import com.github.kornilova203.matlab.refactoring.MatlabIntroduceVariableHandler
import com.intellij.refactoring.util.CommonRefactoringUtil.RefactoringErrorHintException
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("extract")
class ExtractTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun testSingleElement() = doTest()
    fun testExpression() = doTest()
    fun testPartExpression() = doTest()
    fun testQualifiedExpr() = doTest()
    fun testQualifiedExprAfterDot() = doTestCannotPerform()
    fun testAllLine() = doTest()
    fun testCaretAtExpressionEnd() = doTest()
    fun testMakeIndent() = doTest()
    fun testIncorrectSelection() = doTestCannotPerform()
    fun testLeftPartAssign() = doTestCannotPerform()
    fun testFindAllOccurrences() = doTest()
    fun testFindOnlyFunctionOccurrences() = doTest()
    fun testRemoveParen() = doTest()
    fun testRemoveParenInDeclaration() = doTest()

    private fun doTest() {
        myFixture.configureByFile(getTestName(false) + ".m")
        val handler = MatlabIntroduceVariableHandler()
        handler.invoke(myFixture.project, myFixture.editor, myFixture.file, null)
        myFixture.checkResultByFile(getTestName(false) + "_after.m")
    }

    private fun doTestCannotPerform(){
        var thrownExpectedException = false
        try {
            doTest()
        } catch (e: RefactoringErrorHintException) {
            thrownExpectedException = true
        }
        assertTrue(thrownExpectedException)
    }
}