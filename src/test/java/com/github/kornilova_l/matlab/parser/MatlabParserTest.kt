package com.github.kornilova_l.matlab.parser

import com.github.kornilova_l.matlab.MatlabParserDefinition
import com.intellij.testFramework.ParsingTestCase

open class MatlabParserTest : ParsingTestCase("", "m", MatlabParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/resources/parser"
    }

    override fun includeRanges(): Boolean = true

    fun testValidNumbers() = doTest(true)

    fun testValidLoad() = doTest(true)

    fun testValidWhile() = doTest(true)

    fun testValidFor() = doTest(true)

    fun testValidIf() = doTest(true)

    fun testValidFunctionCall() = doTest(true)

    fun testValidFunctionDeclaration() = doTest(true)

    fun testValidClassDeclaration() = doTest(true)

    fun testValidPropertyAccess() = doTest(true)

    fun testStrings() = doTest(true)

    fun testComments() = doTest(true)
}