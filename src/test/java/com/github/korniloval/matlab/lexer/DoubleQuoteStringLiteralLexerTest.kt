package com.github.korniloval.matlab.lexer

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
                DOUBLE_QUOTE_STRING ('""')
                """.trimIndent())

        doTest("""
                "hello"
                """.trimIndent(),
                """
                DOUBLE_QUOTE_STRING ('"hello"')
                """.trimIndent())
    }

    fun testEscapeSequence() {
        doTest("""
                "\n"
                """.trimIndent(),
                """
                DOUBLE_QUOTE_STRING ('"')
                VALID_STRING_ESCAPE_TOKEN ('\n')
                DOUBLE_QUOTE_STRING ('"')
                """.trimIndent())

        doTest("""
                "hello\"\\\n"
                """.trimIndent(),
                """
                DOUBLE_QUOTE_STRING ('"hello')
                VALID_STRING_ESCAPE_TOKEN ('\"')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                VALID_STRING_ESCAPE_TOKEN ('\n')
                DOUBLE_QUOTE_STRING ('"')
                """.trimIndent())
    }

    fun testInvalidString() {
        doTest("""
                "
                """.trimIndent(),
                """
                DOUBLE_QUOTE_STRING ('"')
                """.trimIndent())

        doTest("""
                "\
                """.trimIndent(),
                """
                DOUBLE_QUOTE_STRING ('"')
                INVALID_CHARACTER_ESCAPE_TOKEN ('\')
                """.trimIndent())

        doTest("""
                "\\\"
                """.trimIndent(),
                """
                DOUBLE_QUOTE_STRING ('"')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                VALID_STRING_ESCAPE_TOKEN ('\"')
                """.trimIndent())

        doTest("""
                "\e"
                """.trimIndent(),
                """
                DOUBLE_QUOTE_STRING ('"')
                INVALID_CHARACTER_ESCAPE_TOKEN ('\e')
                DOUBLE_QUOTE_STRING ('"')
                """.trimIndent())
    }
}
