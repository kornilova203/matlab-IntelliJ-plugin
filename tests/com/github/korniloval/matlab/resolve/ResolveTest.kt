package com.github.korniloval.matlab.resolve

import com.github.korniloval.matlab.MatlabReference
import com.github.korniloval.matlab.psi.MatlabDeclaration
import com.intellij.psi.PsiElement
import com.intellij.testFramework.ResolveTestCase
import java.io.File

private const val DECL_MARKER = "<decl>"

class ResolveTest : ResolveTestCase() {

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

    private fun doTest(name: String) {
        val (ref, decl) = getExpectedDeclaration()
        val resolvedDecl = ref.resolve()
        assertNotNull("Declaration not found", resolvedDecl)
        assertEquals(name, resolvedDecl?.nameIdentifier?.text)
        assertEquals(resolvedDecl, decl)
    }

    private fun doTestUnresolved() {
        val element = resolve()
        assertNull(element)
    }

    private fun resolve(): PsiElement? {
        val ref = configureByFile(getTestName(false) + ".m")
        return ref.resolve()
    }

    private fun getExpectedDeclaration(): Pair<MatlabReference, MatlabDeclaration> {
        val fileName = getTestName(false) + ".m"
        val file = File(testDataPath + getTestName(false) + ".m")
        return getExpectedDeclaration(file.readText(), fileName)
    }

    private fun getExpectedDeclaration(fileText: String, fileName: String): Pair<MatlabReference, MatlabDeclaration> {
        var declOffset = fileText.indexOf(DECL_MARKER)
        var refOffset = fileText.indexOf(MARKER)
        val clearText: String
        if (declOffset < refOffset) {
            clearText = cutText(fileText, declOffset, DECL_MARKER.length, refOffset, MARKER.length)
            refOffset -= DECL_MARKER.length
        } else {
            clearText = cutText(fileText, refOffset, MARKER.length, declOffset, DECL_MARKER.length)
            declOffset -= MARKER.length
        }
        myFile = createFile(fileName, clearText)

        var el = myFile.findElementAt(declOffset)
        while (el != null && el !is MatlabDeclaration) {
            el = el.parent
        }
        return myFile.findReferenceAt(refOffset) as MatlabReference to el as MatlabDeclaration
    }

    private fun cutText(text: String, firstMarkerOffset: Int, firstMarkerLength: Int, secondMarkerOffset: Int, secondMarkerLength: Int): String {
        return text.substring(0, firstMarkerOffset) +
                text.substring(firstMarkerOffset + firstMarkerLength, secondMarkerOffset) +
                text.substring(secondMarkerOffset + secondMarkerLength)
    }
}
