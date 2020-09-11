package com.github.kornilova203.matlab.resolve

import com.github.kornilova203.matlab.MatlabReference
import com.github.kornilova203.matlab.psi.MatlabDeclaration
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import java.io.File

private const val DECL_MARKER = "<decl>"
private const val REF_MARKER = "<ref>"

class MultifileResolveTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = "testData/resolve/multifile/"

    fun testFunction() = doTest("FunctionDeclaration")
    fun testClass() = doTest("ClassDeclaration")
    fun testVariableUnresolved() = doTestUnresolved("a")
    fun testGlobal() = doTestMulti("a", 2)
    fun testGlobalUnresolved() = doTestUnresolved("a")
    fun testProperty() = doTest("Prop")
    fun testMethodDotNotation() = doTest("foo")
    fun testConstructor() = doTest("Prop")
    fun testFindClassInAssign() = doTest("Prop")
    fun testFindClassInMethod() = doTest("Prop")    
    fun testFindClassInProp() = doTestWithAdditionalFile("Prop")      
    fun testNestedQualified() = doTestWithAdditionalFile("Prop")   
    fun testMethod() = doTest("foo")

    private fun doTest(name: String, shouldBeResolved: Boolean = true) {
        val testName = testDataPath + getTestName(false)
        val refFile = File(testName + ".m")
        val declFile = File(testName + "Declaration.m")
        val (ref, decl) = getExpectedDeclaration(refFile, declFile)
        val resolvedDecl = ref.resolve()
        if (shouldBeResolved) {
            assertNotNull("Declaration not found", resolvedDecl)
            assertEquals(name, resolvedDecl?.nameIdentifier?.text)
            assertEquals(resolvedDecl, decl)
        } else {
            if (resolvedDecl != null) {
                assertEquals(resolvedDecl.containingFile, ref.element.containingFile)
            }
        }
    }

    private fun doTestUnresolved(name: String) = doTest(name, false)

    private fun doTestMulti(name: String, count: Int) {
        val testName = testDataPath + getTestName(false)
        val refFile = File(testName + ".m")
        val declFile = File(testName + "Declaration.m")
        val ref = getExpectedDeclaration(refFile, declFile).first
        val resolvedDecl = ref.multiResolve(false)
        TestCase.assertEquals(count, resolvedDecl.size)
        for (decl in resolvedDecl) {
            val element = decl.element as MatlabDeclaration
            assertEquals(name, element.nameIdentifier?.text)
        }
    }
    
    private fun doTestWithAdditionalFile(name: String) {
        myFixture.configureByFile(getTestName(false) + "Add.m")
        doTest(name)
    }

    private fun getExpectedDeclaration(refFileOriginal: File, declFileOriginal: File): Pair<MatlabReference, MatlabDeclaration?> {
        val refFileText = refFileOriginal.readText()
        val declFileText = declFileOriginal.readText()
        val refOffset = refFileText.indexOf(REF_MARKER)
        val declOffset = declFileText.indexOf(DECL_MARKER)
        val refClearText = refFileText.cutOut(refOffset, REF_MARKER.length)
        val declClearText = if (declOffset == -1) declFileText else declFileText.cutOut(declOffset, DECL_MARKER.length)

        val refFile = myFixture.configureByText("ref.m", refClearText)
        val declFile = myFixture.configureByText(declFileOriginal.name, declClearText)

        var declElement = if (declOffset == -1) null else declFile.findElementAt(declOffset)
        while (declElement != null && declElement !is MatlabDeclaration) {
            declElement = declElement.parent
        }
        if (declElement != null) assertInstanceOf(declElement, MatlabDeclaration::class.java)
        return refFile.findReferenceAt(refOffset) as MatlabReference to declElement as? MatlabDeclaration
    }

    private fun String.cutOut(markerOffset: Int, markerLength: Int): String {
        return substring(0, markerOffset) + substring(markerOffset + markerLength)
    }
}
