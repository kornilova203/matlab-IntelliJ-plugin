package com.github.kornilova203.matlab.refactoring.inline

import com.github.kornilova203.matlab.MatlabReference
import com.github.kornilova203.matlab.findDeclaration
import com.github.kornilova203.matlab.getTestDataRoot
import com.github.kornilova203.matlab.psi.MatlabAssignExpr
import com.github.kornilova203.matlab.psi.MatlabRefExpr
import com.github.kornilova203.matlab.psi.MatlabResolvingScopeProcessor
import com.github.kornilova203.matlab.refactoring.MatlabInlineVariableHandler
import com.intellij.psi.util.parentOfTypes
import com.intellij.refactoring.util.CommonRefactoringUtil
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase

@TestDataPath("inline")
class InlineVariableTest: BasePlatformTestCase() {
    override fun getTestDataPath(): String =  getTestDataRoot(javaClass)
    
    fun testSimple() = doTest()
    fun testMultiple() = doTest()
    fun testInBlock() = doTest()
    fun testNoInitializer() = doTestCannotPerform("Cannot perform refactoring\nVariable has no initializer")
    fun testUnusedVariable() = doTestCannotPerform("Cannot perform refactoring\nVariable 'a' is never used")
    fun testMultiDeclaration() = doTestCannotPerform("Cannot perform refactoring\nCannot find a single definition to inline")
    fun testDoubleDeclaration() = doTest()
    fun testParenthesis() = doTest()
    fun testInlineUsage() = doTest()
    fun testDeclarationInLoop() = doTestCannotPerform("Cannot perform refactoring\nCannot find a single definition to inline")
    
    private fun doTest() {
        val file = myFixture.configureByFile(getTestName(false) + ".m")
        val handler = MatlabInlineVariableHandler()
        val element = file.findElementAt(myFixture.caretOffset)!!.parentOfTypes(MatlabRefExpr::class)!!
        val decl = MatlabReference(element).resolve()
        handler.inlineElement(myFixture.project, myFixture.editor, decl)
        myFixture.checkResultByFile(getTestName(false) + "_after.m")
    }
    
    private fun doTestCannotPerform(message: String) {
        var thrownExpectedException = false
        try {
            doTest()
        } catch (e: CommonRefactoringUtil.RefactoringErrorHintException) {
            thrownExpectedException = true
            TestCase.assertEquals(message, e.message)
        }
        assertTrue(thrownExpectedException)
    }
}