package com.github.kornilova203.matlab.resolve

import com.github.kornilova203.matlab.MatlabReference
import com.github.kornilova203.matlab.psi.MatlabDeclaration
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.io.File

private const val DECL_MARKER = "<decl>"
private const val REF_MARKER = "<ref>"

class ResolveTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = "testData/resolve/"

    fun testVariable() = doTest("a")
    fun testVariableInIf() = doTest("a")
    fun testVariableInCondition() = doTest("a")
    fun testVariableInAssignment() = doTest("a")
    fun testVariableUnresolved() = doTestUnresolved()
    fun testFunction() = doTest("fun")
    fun testFunctionUnresolved() = doTestUnresolved()
    fun testFunctionInFunction() = doTest("fun")
    fun testFunctionInBlock() = doTest("fun")
    fun testFunctionInBlockUnresolved() = doTestUnresolved()
    fun testVarInsideFunctionUnresolved() = doTestUnresolved()
    fun testClass() = doTest("hit_list")
    fun testReturnValue() = doTest("retVal")
    fun testParameter() = doTest("param")
    fun testForLoop() = doTest("ii")
    fun testLambdaParameter() = doTest("x")
    fun testVariableUsedInLambda() = doTest("a")
    fun testCaughtException() = doTest("e")
    fun testVariableInCatchBlock() = doTest("a")
    fun testGlobal() = doTest("b")

    private fun doTest(name: String, shouldBeResolved: Boolean = true) {
        val file = File(testDataPath + getTestName(false) + ".m")
        val (ref, decl) = getExpectedDeclaration(file.readText())
        val resolvedDecl = ref.resolve()
        if (shouldBeResolved) {
            assertNotNull("Declaration not found", resolvedDecl)
            assertEquals(name, resolvedDecl?.nameIdentifier?.text)
            assertEquals(resolvedDecl, decl)
        }
        else {
            assertNull("Declaration should not be found", resolvedDecl)
        }
    }

    private fun doTestUnresolved() = doTest(name, false)

    private fun getExpectedDeclaration(fileText: String): Pair<MatlabReference, MatlabDeclaration?> {
        var declOffset = fileText.indexOf(DECL_MARKER)
        var refOffset = fileText.indexOf(REF_MARKER)
        val clearText: String = when {
            declOffset == -1 -> {
                fileText.cutOut(refOffset, REF_MARKER.length)
            }
            declOffset < refOffset -> {
                refOffset -= DECL_MARKER.length
                fileText.cutOut(declOffset, DECL_MARKER.length).cutOut(refOffset, REF_MARKER.length)
            }
            else -> {
                declOffset -= REF_MARKER.length
                fileText.cutOut(refOffset, REF_MARKER.length).cutOut(declOffset, DECL_MARKER.length)
            }
        }
        val file = myFixture.configureByText("a.m", clearText)

        var el = if (declOffset == -1) null else file.findElementAt(declOffset)
        while (el != null && el !is MatlabDeclaration) {
            el = el.parent
        }
        if (el != null) assertInstanceOf(el, MatlabDeclaration::class.java)
        return file.findReferenceAt(refOffset) as MatlabReference to el as? MatlabDeclaration
    }

    private fun String.cutOut(markerOffset: Int, markerLength: Int): String {
        return substring(0, markerOffset) + substring(markerOffset + markerLength)
    }
}
