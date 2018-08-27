package com.github.korniloval.matlab.lexer

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase

open class SingleQuoteStringLiteralLexerTest : LexerTestCase() {
    override fun getDirPath(): String = ""

    override fun createLexer(): Lexer = SingleQuoteStringLexer.getAdapter()

    fun testSimpleCases() {
        doTest("""
               ''
               """.trimIndent(),
                """
               SINGLE_QUOTE_STRING ('''')
               """.trimIndent())

        doTest("""
               'hello'
               """.trimIndent(),
                """
               SINGLE_QUOTE_STRING (''hello'')
               """.trimIndent())
    }

    fun testEscapeSequence() {
        doTest("""
                '\n'
                """.trimIndent(),
                """
                SINGLE_QUOTE_STRING (''')
                VALID_STRING_ESCAPE_TOKEN ('\n')
                SINGLE_QUOTE_STRING (''')
                """.trimIndent())

        doTest("""
                ''''
                """.trimIndent(),
                """
                SINGLE_QUOTE_STRING (''')
                VALID_STRING_ESCAPE_TOKEN ('''')
                SINGLE_QUOTE_STRING (''')
                """.trimIndent())

        doTest("""
                'hello\t'
                """.trimIndent(),
                """
                SINGLE_QUOTE_STRING (''hello')
                VALID_STRING_ESCAPE_TOKEN ('\t')
                SINGLE_QUOTE_STRING (''')
                """.trimIndent())
    }

    fun testStringWithSlashes() {
        doTest("""
                '\'
                """.trimIndent(),
                """
                SINGLE_QUOTE_STRING (''\'')
                """.trimIndent())

        doTest("""
                '\\'
                """.trimIndent(),
                """
                SINGLE_QUOTE_STRING (''')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                SINGLE_QUOTE_STRING (''')
                """.trimIndent())

        doTest("""
                '\\\hello'
                """.trimIndent(),
                """
                SINGLE_QUOTE_STRING (''')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                SINGLE_QUOTE_STRING ('\hello'')
                """.trimIndent())

        doTest("""
                '\\\n hello'
                """.trimIndent(),
                """
                SINGLE_QUOTE_STRING (''')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                VALID_STRING_ESCAPE_TOKEN ('\n')
                SINGLE_QUOTE_STRING (' hello'')
                """.trimIndent())

        doTest("""
                '\'''
                """.trimIndent(),
                """
                SINGLE_QUOTE_STRING (''\')
                VALID_STRING_ESCAPE_TOKEN ('''')
                SINGLE_QUOTE_STRING (''')
                """.trimIndent())
    }

    fun testInvalidString() {
        doTest("""
                '
                """.trimIndent(),
                """
                SINGLE_QUOTE_STRING (''')
                """.trimIndent())

        doTest("""
                '\
                """.trimIndent(),
                """
                SINGLE_QUOTE_STRING (''\')
                """.trimIndent())

        doTest("""
                '''
                """.trimIndent(),
                """
                SINGLE_QUOTE_STRING (''')
                VALID_STRING_ESCAPE_TOKEN ('''')
                """.trimIndent())
    }
}
