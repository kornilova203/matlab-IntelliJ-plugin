package com.github.kornilova_l.matlab.lexer

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase

open class SingleQuoteStringLiteralLexerTest : LexerTestCase() {
    override fun getDirPath(): String = ""

    override fun createLexer(): Lexer = SingleQuoteStringLexer.getAdapter()

    fun testSimpleCases() {
        doTest("''",
                "MatlabTokenType.SINGLE_QUOTE_STRING ('''')")

        doTest("'hello'",
                "MatlabTokenType.SINGLE_QUOTE_STRING (''hello'')")
    }

    fun testEscapeSequence() {
        doTest("""
                '\n'
                """.trimIndent(),
                """
                MatlabTokenType.SINGLE_QUOTE_STRING (''')
                VALID_STRING_ESCAPE_TOKEN ('\n')
                MatlabTokenType.SINGLE_QUOTE_STRING (''')
                """.trimIndent())

        doTest("""
                ''''
                """.trimIndent(),
                """
                MatlabTokenType.SINGLE_QUOTE_STRING (''')
                VALID_STRING_ESCAPE_TOKEN ('''')
                MatlabTokenType.SINGLE_QUOTE_STRING (''')
                """.trimIndent())

        doTest("""
                'hello\t'
                """.trimIndent(),
                """
                MatlabTokenType.SINGLE_QUOTE_STRING (''hello')
                VALID_STRING_ESCAPE_TOKEN ('\t')
                MatlabTokenType.SINGLE_QUOTE_STRING (''')
                """.trimIndent())
    }

    fun testStringWithSlashes() {
        doTest("""
                '\'
                """.trimIndent(),
                """
                MatlabTokenType.SINGLE_QUOTE_STRING (''\'')
                """.trimIndent())

        doTest("""
                '\\'
                """.trimIndent(),
                """
                MatlabTokenType.SINGLE_QUOTE_STRING (''')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                MatlabTokenType.SINGLE_QUOTE_STRING (''')
                """.trimIndent())

        doTest("""
                '\\\hello'
                """.trimIndent(),
                """
                MatlabTokenType.SINGLE_QUOTE_STRING (''')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                MatlabTokenType.SINGLE_QUOTE_STRING ('\hello'')
                """.trimIndent())

        doTest("""
                '\\\n hello'
                """.trimIndent(),
                """
                MatlabTokenType.SINGLE_QUOTE_STRING (''')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                VALID_STRING_ESCAPE_TOKEN ('\n')
                MatlabTokenType.SINGLE_QUOTE_STRING (' hello'')
                """.trimIndent())

        doTest("""
                '\'''
                """.trimIndent(),
                """
                MatlabTokenType.SINGLE_QUOTE_STRING (''\')
                VALID_STRING_ESCAPE_TOKEN ('''')
                MatlabTokenType.SINGLE_QUOTE_STRING (''')
                """.trimIndent())
    }

    fun testInvalidString() {
        doTest("""
                '
                """.trimIndent(),
                """
                MatlabTokenType.SINGLE_QUOTE_STRING (''')
                """.trimIndent())

        doTest("""
                '\
                """.trimIndent(),
                """
                MatlabTokenType.SINGLE_QUOTE_STRING (''\')
                """.trimIndent())

        doTest("""
                '''
                """.trimIndent(),
                """
                MatlabTokenType.SINGLE_QUOTE_STRING (''')
                VALID_STRING_ESCAPE_TOKEN ('''')
                """.trimIndent())
    }
}
