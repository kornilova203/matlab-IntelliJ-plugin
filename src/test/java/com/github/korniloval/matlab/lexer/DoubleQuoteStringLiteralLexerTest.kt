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
                MatlabTokenType.DOUBLE_QUOTE_STRING ('""')
                """.trimIndent())

        doTest("""
                "hello"
                """.trimIndent(),
                """
                MatlabTokenType.DOUBLE_QUOTE_STRING ('"hello"')
                """.trimIndent())
    }

    fun testEscapeSequence() {
        doTest("""
                "\n"
                """.trimIndent(),
                """
                MatlabTokenType.DOUBLE_QUOTE_STRING ('"')
                VALID_STRING_ESCAPE_TOKEN ('\n')
                MatlabTokenType.DOUBLE_QUOTE_STRING ('"')
                """.trimIndent())

        doTest("""
                "hello\"\\\n"
                """.trimIndent(),
                """
                MatlabTokenType.DOUBLE_QUOTE_STRING ('"hello')
                VALID_STRING_ESCAPE_TOKEN ('\"')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                VALID_STRING_ESCAPE_TOKEN ('\n')
                MatlabTokenType.DOUBLE_QUOTE_STRING ('"')
                """.trimIndent())
    }

    fun testInvalidString() {
        doTest("""
                "
                """.trimIndent(),
                """
                MatlabTokenType.DOUBLE_QUOTE_STRING ('"')
                """.trimIndent())

        doTest("""
                "\
                """.trimIndent(),
                """
                MatlabTokenType.DOUBLE_QUOTE_STRING ('"')
                INVALID_CHARACTER_ESCAPE_TOKEN ('\')
                """.trimIndent())

        doTest("""
                "\\\"
                """.trimIndent(),
                """
                MatlabTokenType.DOUBLE_QUOTE_STRING ('"')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                VALID_STRING_ESCAPE_TOKEN ('\"')
                """.trimIndent())

        doTest("""
                "\e"
                """.trimIndent(),
                """
                MatlabTokenType.DOUBLE_QUOTE_STRING ('"')
                INVALID_CHARACTER_ESCAPE_TOKEN ('\e')
                MatlabTokenType.DOUBLE_QUOTE_STRING ('"')
                """.trimIndent())
    }
}
