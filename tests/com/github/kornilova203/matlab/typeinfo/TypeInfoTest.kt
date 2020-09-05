package com.github.kornilova203.matlab.typeinfo

import com.github.kornilova203.matlab.MatlabTypeProvider
import com.github.kornilova203.matlab.getTestDataRoot
import com.github.kornilova203.matlab.psi.MatlabRefExpr
import com.intellij.psi.util.parentOfTypes
import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase

@TestDataPath("typeinfo")
class TypeInfoTest: BasePlatformTestCase() {
    
    fun testScalar() = doTest("Scalar")
    fun testString() = doTest("String")
    fun testMatrix() = doTest("Matrix")
    fun testCell() = doTest("Cell")
    fun testBool() = doTest("Bool")
    fun testStruct() = doTest("Struct")
    fun testFunction() = doTest("Function")
    fun testClass() {
        myFixture.configureByText("MyClass.m", "classdef MyClass end")
        doTest("MyClass")
    }
    fun testAssign() = doTest("String")
    fun testExpr() = doTest("Scalar")
    
    
    fun doTest(type: String) {
        val file = myFixture.configureByFile(getTestName(false) + ".m")
        val element = file.findElementAt(myFixture.caretOffset)?.parentOfTypes(MatlabRefExpr::class)!!
        TestCase.assertEquals(type, MatlabTypeProvider().getInformationHint(element))
    }

    override fun getTestDataPath(): String = getTestDataRoot(javaClass)
}