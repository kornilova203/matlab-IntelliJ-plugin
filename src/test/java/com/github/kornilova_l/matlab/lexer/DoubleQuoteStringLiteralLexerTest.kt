package com.github.kornilova_l.matlab.lexer

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase

open class DoubleQuoteStringLiteralLexerTest : LexerTestCase() {
    override fun getDirPath(): String = ""

    override fun createLexer(): Lexer = DoubleQuoteStringLiteralLexer()

    fun testSimpleCases() {
        doTest("""
                ""
                """.trimIndent(),
                """
                MatlabTokenType.doublequotestringliteral ('""')
                """.trimIndent())

        doTest("""
                "hello"
                """.trimIndent(),
                """
                MatlabTokenType.doublequotestringliteral ('"hello"')
                """.trimIndent())
    }

    fun testEscapeSequence() {
        doTest("""
                "\n"
                """.trimIndent(),
                """
                MatlabTokenType.doublequotestringliteral ('"')
                VALID_STRING_ESCAPE_TOKEN ('\n')
                MatlabTokenType.doublequotestringliteral ('"')
                """.trimIndent())

        doTest("""
                "hello\"\\\n"
                """.trimIndent(),
                """
                MatlabTokenType.doublequotestringliteral ('"hello')
                VALID_STRING_ESCAPE_TOKEN ('\"')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                VALID_STRING_ESCAPE_TOKEN ('\n')
                MatlabTokenType.doublequotestringliteral ('"')
                """.trimIndent())
    }

    fun testInvalidString() {
        doTest("""
                "
                """.trimIndent(),
                """
                MatlabTokenType.doublequotestringliteral ('"')
                """.trimIndent())

        doTest("""
                "\
                """.trimIndent(),
                """
                MatlabTokenType.doublequotestringliteral ('"')
                INVALID_CHARACTER_ESCAPE_TOKEN ('\')
                """.trimIndent())

        doTest("""
                "\\\"
                """.trimIndent(),
                """
                MatlabTokenType.doublequotestringliteral ('"')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                VALID_STRING_ESCAPE_TOKEN ('\"')
                """.trimIndent())

        doTest("""
                "\e"
                """.trimIndent(),
                """
                MatlabTokenType.doublequotestringliteral ('"')
                INVALID_CHARACTER_ESCAPE_TOKEN ('\e')
                MatlabTokenType.doublequotestringliteral ('"')
                """.trimIndent())
    }
}
