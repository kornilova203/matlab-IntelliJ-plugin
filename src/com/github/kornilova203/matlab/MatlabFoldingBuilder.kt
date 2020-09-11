package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.MatlabFile
import com.github.kornilova203.matlab.psi.MatlabTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.CustomFoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType.WHITE_SPACE


class MatlabFoldingBuilder : CustomFoldingBuilder() {
    override fun buildLanguageFoldRegions(descriptors: MutableList<FoldingDescriptor>, root: PsiElement, document: Document, quick: Boolean) {
        if (root !is MatlabFile) {
            return
        }

        appendDescriptors(root.node, descriptors)
    }

    private fun appendDescriptors(node: ASTNode, descriptors: MutableList<FoldingDescriptor>) {
        val type = node.elementType
        val parentType = node.treeParent?.elementType
        if (type == MatlabTypes.BLOCK && parentType != MatlabTypes.CASE_BLOCK && parentType != MatlabTypes.OTHERWISE_BLOCK
                || type == MatlabTypes.SWITCH_BLOCK || type == MatlabTypes.CLASS_DECLARATION || type == MatlabTypes.EVENTS_LIST 
                || type == MatlabTypes.PROPERTIES_LIST || type == MatlabTypes.METHODS_LIST) {
            val start = getStartOffset(node)
            val end = getEndOffset(node)
            if (end > start) {
                val textRange = TextRange(start, end)
                descriptors.add(FoldingDescriptor(node, textRange))
            }
        }

        var child = node.firstChildNode
        while (child != null) {
            appendDescriptors(child, descriptors)
            child = child.treeNext
        }
    }

    private fun getStartOffset(node: ASTNode): Int {
        val child = when (node.elementType) {
            MatlabTypes.SWITCH_BLOCK -> node.findChildByType(MatlabTypes.SWITCH_EXPRESSION)
            MatlabTypes.CLASS_DECLARATION ->
                if (node.findChildByType(MatlabTypes.SUPER_CLASSES) != null) node.findChildByType(MatlabTypes.SUPER_CLASSES)
                else node.findChildByType(MatlabTypes.IDENTIFIER)
            else -> null
        }
        if (child != null) {
            return child.textRange.endOffset
        }

        var prev = node.treePrev
        while (prev != null) {
            val type = prev.elementType
            if (type != WHITE_SPACE && type != MatlabTypes.NEWLINE) {
                return prev.textRange.endOffset
            }
            prev = prev.treePrev
        }
        return node.textRange.startOffset
    }

    private fun getEndOffset(node: ASTNode): Int {
        val type = node.elementType
        if (type == MatlabTypes.SWITCH_BLOCK || type == MatlabTypes.CLASS_DECLARATION) {
            return node.textRange.endOffset - MatlabTypes.END.toString().length
        }
        return findNotSpaceEnd(node)
    }

    private fun findNotSpaceEnd(node: ASTNode): Int {
        var next = node.treeNext
        while (next != null) {
            val type = next.elementType
            if (type != WHITE_SPACE && type != MatlabTypes.NEWLINE) {
                return next.textRange.startOffset
            }
            next = next.treeNext
        }
        return if (node.treeParent != null) findNotSpaceEnd(node.treeParent) else node.textRange.endOffset
    }

    override fun isRegionCollapsedByDefault(node: ASTNode): Boolean = false

    override fun getLanguagePlaceholderText(node: ASTNode, range: TextRange): String = "..."
}
