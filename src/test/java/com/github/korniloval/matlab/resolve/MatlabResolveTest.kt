package com.github.korniloval.matlab.resolve

import com.github.korniloval.matlab.psi.MatlabParameters
import com.github.korniloval.matlab.psi.MatlabReturnValue
import com.github.korniloval.matlab.psi.MatlabReturnValues
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.ResolveTestCase

class MatlabResolveTest : ResolveTestCase() {
    override fun getTestDataPath(): String = "src/test/resources/resolve/"

    fun testVariable() = doTest("a")
    fun testVariableInIf() = doTest("a")
    fun testVariableInCondition() = doTest("a")
    fun testVariableUnresolved() = doTestUnresolved()
    fun testFunction() = doTest("fun")
    fun testFunctionUnresolved() = doTestUnresolved()
    fun testClass() = doTest("hit_list")
    fun testReturnValue() {
        val element = resolve()
        assertNotNull(element)
        assertEquals("retVal", element!!.text)
        assertNotNull(PsiTreeUtil.findFirstParent(element) { it is MatlabReturnValue || it is MatlabReturnValues })
    }

    fun testParameter() {
        val element = resolve()
        assertNotNull(element)
        assertEquals("param", element!!.text)
        assertNotNull(PsiTreeUtil.findFirstParent(element) { it is MatlabParameters })
    }

    private fun doTest(name: String) {
        val element = resolve()
        assertNotNull(element)
        assertEquals(name, element!!.text)
    }

    private fun doTestUnresolved() {
        val element = resolve()
        assertNull(element)
    }

    private fun resolve(): PsiElement? {
        val ref = configureByFile(getTestName(false) + ".m")
        return ref.resolve()
    }
}
