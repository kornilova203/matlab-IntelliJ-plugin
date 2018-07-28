package com.github.kornilova_l.matlab.lexer

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase

open class SingleQuoteStringLiteralLexerTest : LexerTestCase() {
    override fun getDirPath(): String = ""

    override fun createLexer(): Lexer = SingleQuoteStringLiteralLexer.getAdapter()

    fun testSimpleCases() {
        doTest("''",
                "MatlabTokenType.singlequotestringliteral ('''')")

        doTest("'hello'",
                "MatlabTokenType.singlequotestringliteral (''hello'')")
    }

    fun testEscapeSequence() {
        doTest("""
                '\n'
                """.trimIndent(),
                """
                MatlabTokenType.singlequotestringliteral (''')
                VALID_STRING_ESCAPE_TOKEN ('\n')
                MatlabTokenType.singlequotestringliteral (''')
                """.trimIndent())

        doTest("""
                ''''
                """.trimIndent(),
                """
                MatlabTokenType.singlequotestringliteral (''')
                VALID_STRING_ESCAPE_TOKEN ('''')
                MatlabTokenType.singlequotestringliteral (''')
                """.trimIndent())

        doTest("""
                'hello\t'
                """.trimIndent(),
                """
                MatlabTokenType.singlequotestringliteral (''hello')
                VALID_STRING_ESCAPE_TOKEN ('\t')
                MatlabTokenType.singlequotestringliteral (''')
                """.trimIndent())
    }

    fun testStringWithSlashes() {
        doTest("""
                '\'
                """.trimIndent(),
                """
                MatlabTokenType.singlequotestringliteral (''\'')
                """.trimIndent())

        doTest("""
                '\\'
                """.trimIndent(),
                """
                MatlabTokenType.singlequotestringliteral (''')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                MatlabTokenType.singlequotestringliteral (''')
                """.trimIndent())

        doTest("""
                '\\\hello'
                """.trimIndent(),
                """
                MatlabTokenType.singlequotestringliteral (''')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                MatlabTokenType.singlequotestringliteral ('\hello'')
                """.trimIndent())

        doTest("""
                '\\\n hello'
                """.trimIndent(),
                """
                MatlabTokenType.singlequotestringliteral (''')
                VALID_STRING_ESCAPE_TOKEN ('\\')
                VALID_STRING_ESCAPE_TOKEN ('\n')
                MatlabTokenType.singlequotestringliteral (' hello'')
                """.trimIndent())

        doTest("""
                '\'''
                """.trimIndent(),
                """
                MatlabTokenType.singlequotestringliteral (''\')
                VALID_STRING_ESCAPE_TOKEN ('''')
                MatlabTokenType.singlequotestringliteral (''')
                """.trimIndent())
    }

    fun testInvalidString() {
        doTest("""
                '
                """.trimIndent(),
                """
                MatlabTokenType.singlequotestringliteral (''')
                """.trimIndent())

        doTest("""
                '\
                """.trimIndent(),
                """
                MatlabTokenType.singlequotestringliteral (''\')
                """.trimIndent())

        doTest("""
                '''
                """.trimIndent(),
                """
                MatlabTokenType.singlequotestringliteral (''')
                VALID_STRING_ESCAPE_TOKEN ('''')
                """.trimIndent())
    }
}
