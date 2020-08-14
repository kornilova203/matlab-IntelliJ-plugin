package com.github.kornilova203.matlab.documentation

import com.github.kornilova203.matlab.MatlabDocumentationProvider
import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.codeInsight.documentation.DocumentationManager
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("documentation")
class DocumentationTest: BasePlatformTestCase() {
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)
    
    fun testVariable() = doTest()
    fun testGlobalVariable() = doTest()
    fun testParameter() = doTest()
    fun testRetValue() = doTest()
    fun testUserFunction() = doTest()
    fun testLibraryFunction() = doTest()
    fun testClass() = doTest()    
    
    private fun doTest() {
        val file = myFixture.configureByFile(getTestName(false) + ".m")
        val originalElement = file.findElementAt(myFixture.caretOffset)!!
        val manager = DocumentationManager.getInstance(myFixture.project)
        val element = manager.findTargetElement(myFixture.editor, originalElement.textOffset, myFixture.file, originalElement)
        val provider = MatlabDocumentationProvider()
        val actual = provider.generateDoc(element, originalElement)!!
        UsefulTestCase.assertSameLinesWithFile(testDataPath + "/" + getTestName(false) + ".html", actual)
    }
}