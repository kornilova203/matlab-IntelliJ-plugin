package com.github.kornilova203.matlab.resolve

import com.github.kornilova203.matlab.MatlabReference
import com.github.kornilova203.matlab.psi.MatlabDeclaration
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.io.File

private const val DECL_MARKER = "<decl>"
private const val REF_MARKER = "<ref>"

class MultifileResolveTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = "testData/resolve/multifile/"

    fun testFunction() = doTest("FunctionDeclaration")
    fun testClass() = doTest("ClassDeclaration")
    fun testVariableUnresolved() = doTestUnresolved()

    private fun doTest(name: String, shouldBeResolved: Boolean = true) {
        val testName = testDataPath + getTestName(false)
        val refFile = File(testName + ".m")
        val declFile = File(testName + "Declaration.m")
        val (ref, decl) = getExpectedDeclaration(refFile.readText(), declFile.readText())
        val resolvedDecl = ref.resolve()
        if (shouldBeResolved) {
            assertNotNull("Declaration not found", resolvedDecl)
            assertEquals(name, resolvedDecl?.nameIdentifier?.text)
            assertEquals(resolvedDecl, decl)
        } else {
            assertNull("Declaration should not be found", resolvedDecl)
        }
    }

    private fun doTestUnresolved() = doTest(name, false)

    private fun getExpectedDeclaration(refFileText: String, declFileText: String): Pair<MatlabReference, MatlabDeclaration?> {
        val refOffset = refFileText.indexOf(REF_MARKER)
        val declOffset = declFileText.indexOf(DECL_MARKER)
        val refClearText = refFileText.cutOut(refOffset, REF_MARKER.length)
        val declClearText = if (declOffset == -1) declFileText else declFileText.cutOut(declOffset, DECL_MARKER.length)

        val refFile = myFixture.configureByText("ref.m", refClearText)
        val declFile = myFixture.configureByText("decl.m", declClearText)

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
