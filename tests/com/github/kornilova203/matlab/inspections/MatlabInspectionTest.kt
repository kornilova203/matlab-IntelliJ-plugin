package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.getTestDataRoot
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.testFramework.fixtures.BasePlatformTestCase

abstract class MatlabInspectionTest : BasePlatformTestCase(){
    override fun getTestDataPath(): String = getTestDataRoot(javaClass)
    
    fun doTest(checkQuickFix: Boolean, inspections: Class<out LocalInspectionTool?>) {
        myFixture.configureByFile(getTestName(false) + ".m")
        myFixture.enableInspections(inspections)
        myFixture.checkHighlighting()
        if (checkQuickFix) {
            val action = myFixture.findSingleIntention("Remove")
            myFixture.launchAction(action)
            myFixture.checkResultByFile(getTestName(false) + "_after.m")
        }
    }
}