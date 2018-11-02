package com.github.korniloval.matlab.resolve

import com.github.korniloval.matlab.psi.MatlabReference
import com.intellij.psi.PsiElement
import com.intellij.testFramework.ResolveTestCase
import junit.framework.TestCase
import java.io.File

private const val DECL_MARKER = "<decl>"

class ResolveTest : ResolveTestCase() {

    override fun getTestDataPath(): String = "testData/resolve/"

    fun testVariable() = doTest("a")
    fun testVariableInIf() = doTest("a")
    fun testVariableInCondition() = doTest("a")
    fun testVariableUnresolved() = doTestUnresolved()
    fun testFunction() = doTest("fun")
    fun testFunctionUnresolved() = doTestUnresolved()
    fun testClass() = doTest("hit_list")
    fun testReturnValue() = doTest("retVal")
    fun testParameter() = doTest("param")
    fun testForLoop() = doTest("ii")

    private fun doTest(name: String) {
        val (ref, decl) = getExpectedDeclaration()
        val element = ref.resolve()
        assertNotNull(element)
        assertEquals(name, element!!.text)
        TestCase.assertEquals(element, decl.myElement)
    }

    private fun doTestUnresolved() {
        val element = resolve()
        assertNull(element)
    }

    private fun resolve(): PsiElement? {
        val ref = configureByFile(getTestName(false) + ".m")
        return ref.resolve()
    }

    private fun getExpectedDeclaration(): Pair<MatlabReference, MatlabReference> {
        val fileName = getTestName(false) + ".m"
        val file = File(testDataPath + getTestName(false) + ".m")
        return getExpectedDeclaration(file.readText(), fileName)
    }

    private fun getExpectedDeclaration(fileText: String, fileName: String): Pair<MatlabReference, MatlabReference> {
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
        return myFile.findReferenceAt(refOffset) as MatlabReference to myFile.findReferenceAt(declOffset) as MatlabReference
    }

    private fun cutText(text: String, firstMarkerOffset: Int, firstMarkerLength: Int, secondMarkerOffset: Int, secondMarkerLength: Int): String {
        return text.substring(0, firstMarkerOffset) +
                text.substring(firstMarkerOffset + firstMarkerLength, secondMarkerOffset) +
                text.substring(secondMarkerOffset + secondMarkerLength)
    }
}
