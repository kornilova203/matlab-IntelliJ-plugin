package com.github.kornilova203.matlab.completion

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.io.File

@TestDataPath("completion")
class CompletionTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)

    fun testF() = doTest("function", "for", "classdef", "if")
    fun testIf() = doTest("function", "for", "classdef", "if", "end", "while", "return")
    fun testWhileCondition() = doTest()
    fun testVarInSameScope() = doTest("var1", "var2")
    fun testVarInDifferentScopes() = doTest("var1", "var2")
    fun testShadowingVar() = doTest("var1", "var2")
    fun testNumber() = doTest()
    fun testNumberDot() = doTest()
    fun testWhile() = doTest("function", "for", "classdef", "if", "end", "while", "return", "continue", "break")
    fun testPackage() = doTest("apputil", "engine", "exception", "lang", "system", "types")
    fun testClass() = doTest("properties", "methods", "events", "enumeration", "end")
    fun testClassName() = doTestMultiFile("ClassNameAdd")
    fun testFunction() = doTestMultiFile("FunctionAdd")
    fun testFunctionWithParenth() = doTestMultiFile("FunctionWithParenthAdd")
    fun testEndInProperties() = doTest("end")
    fun testEndInMethods() = doTest("end")
    fun testEndInEvents() = doTest("end")
    fun testEndInEnumeration() = doTest("end")
    fun testFunctionInMethods() = doTest("function")

    private fun doTest(vararg completionVariants: String) {
        myFixture.configureByFile(getTestFilePath())
        myFixture.complete(CompletionType.BASIC)
        val variants = myFixture.lookupElementStrings
        UsefulTestCase.assertContainsElements(variants!!, *completionVariants)
        val afterFile = testDataPath + "/" + getTestName(false) + ".after.m"
        if (File(afterFile).exists()) {
            myFixture.finishLookup('\n')
            val actual = myFixture.file.text.replace("(^|.*[^ ]) +$".toRegex(RegexOption.MULTILINE), "$0% blank")
            UsefulTestCase.assertSameLinesWithFile(afterFile, actual)
        }
    }
    
    private fun doTestMultiFile(vararg completionVariants: String) {
        myFixture.configureByFile(getTestName(false) + "Add.m")
        doTest(*completionVariants)
    }

    private fun getTestFilePath(): String = testDataPath + "/" + getTestName(false) + ".m"
}
