package com.github.kornilova_l.matlab

import com.github.kornilova_l.matlab.psi.MatlabFile
import com.github.kornilova_l.matlab.psi.MatlabTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class MatlabParserDefinition : ParserDefinition {
    companion object {
        val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE)
        val COMMENTS = TokenSet.create(MatlabTypes.COMMENT)
        val FILE = IFileElementType(MatlabLanguage.INSTANCE)
    }

    override fun createParser(project: Project?): PsiParser = MatlabParser()

    override fun createFile(viewProvider: FileViewProvider?): PsiFile? {
        if (viewProvider == null) {
            return null
        }
        return MatlabFile(viewProvider)
    }

    override fun spaceExistenceTypeBetweenTokens(p0: ASTNode?, p1: ASTNode?): ParserDefinition.SpaceRequirements {
        return ParserDefinition.SpaceRequirements.MAY
    }

    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun getFileNodeType(): IFileElementType = FILE

    override fun getWhitespaceTokens(): TokenSet = WHITE_SPACES

    override fun createLexer(p0: Project?): Lexer = MatlabLexerAdapter()

    override fun createElement(node: ASTNode?): PsiElement {
        throw AssertionError("Unknown element type: ${node!!.elementType}")
    }

    override fun getCommentTokens(): TokenSet = COMMENTS
}