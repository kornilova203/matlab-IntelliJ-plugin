package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.MatlabTypes
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.highlighter.HighlighterIterator
import com.intellij.psi.tree.IElementType
import com.intellij.util.text.CharArrayUtil

class MatlabSemanticEditorPosition(editor: EditorEx, offset: Int) {
    private var iterator: HighlighterIterator = editor.highlighter.createIterator(offset)
    private var editor: EditorEx = editor
    private var chars: CharSequence = this.editor.document.charsSequence

    fun isAtEnd(): Boolean {
        return iterator.atEnd()
    }

    fun isAt(element: IElementType) : Boolean {
        if (!iterator.atEnd()) {
            val type = getType()
            return type == element
        }
        return false
    }

    fun isAtAnyOf(vararg elements: IElementType?): Boolean {
        if (!iterator.atEnd()) {
            val type = getType()
            for (element in elements) {
                if (type == element) return true
            }
        }
        return false
    }

    private fun isAtMultiline(): Boolean {
        return if (!iterator.atEnd()) {
            CharArrayUtil.containLineBreaks(chars, iterator.start, iterator.end)
        } else false
    }

    fun startOffset(): Int {
        return iterator.start
    }

    private fun moveAfter() {
        if (!iterator.atEnd()) {
            iterator.advance()
        }
    }

    fun after(): MatlabSemanticEditorPosition {
        return copyAnd { position: MatlabSemanticEditorPosition -> position.moveAfter() }
    }

    private fun moveBeforeOptionalMix(vararg elements: IElementType?) {
        while (isAtAnyOf(*elements)) {
            iterator.retreat()
        }
    }

    fun beforeOptionalMix(vararg elements: IElementType): MatlabSemanticEditorPosition {
        return copyAnd { position: MatlabSemanticEditorPosition -> position.moveBeforeOptionalMix(*elements)}
    }

    private fun moveAfterOptionalMix(vararg elements: IElementType?) {
        while (isAtAnyOf(*elements)) {
            iterator.advance()
        }
    }

    fun afterOptionalMix(vararg elements: IElementType): MatlabSemanticEditorPosition {
        return copyAnd { position: MatlabSemanticEditorPosition -> position.moveAfterOptionalMix(*elements) }
    }

    private fun moveToLeftParenthesisBackwardsSkippingNested(leftParenthesis: Collection<IElementType>, rightParenthesis: Collection<IElementType>) {
        while (!iterator.atEnd()) {
            val type = getType()
            if (rightParenthesis.contains(type)) {
                moveBeforeParentheses(leftParenthesis, rightParenthesis)
                continue
            } else if (leftParenthesis.contains(type)) {
                break
            }
            iterator.retreat()
        }
    }

    private fun moveBeforeParentheses(leftParenthesis: Collection<IElementType>, rightParenthesis: Collection<IElementType>) {
        var parenLevel = 0
        while (!iterator.atEnd()) {
            val type = getType()
            iterator.retreat()
            if (rightParenthesis.contains(type)) {
                parenLevel++
            } else if (leftParenthesis.contains(type)) {
                parenLevel--
                if (parenLevel < 1) {
                    break
                }
            }
        }
    }

    fun findLeftParenthesisBackwardsSkippingNested(leftParenthesis: Collection<IElementType>,
                                                   rightParenthesis: Collection<IElementType>): MatlabSemanticEditorPosition {
        return copyAnd { position -> position.moveToLeftParenthesisBackwardsSkippingNested(leftParenthesis, rightParenthesis)}
    }

    fun isAfterOnSameLine(vararg elements: IElementType): Boolean {
        return elementAfterOnSameLine(*elements) != null
    }

    private fun elementAfterOnSameLine(vararg elements: IElementType): IElementType? {
        iterator.retreat()
        while (!iterator.atEnd() && !isAtMultiline()) {
            val type = getType()
            for (element in elements) {
                if (type == element) return element
            }
            iterator.retreat()
        }
        return null
    }

    private fun copyAnd(modifier: (MatlabSemanticEditorPosition) -> Unit): MatlabSemanticEditorPosition {
        val position: MatlabSemanticEditorPosition = copy()
        modifier(position)
        return position
    }

    private fun copy(): MatlabSemanticEditorPosition {
        return MatlabSemanticEditorPosition(editor, if (isAtEnd()) -1 else iterator.start)
    }

    private fun getType() : IElementType {
        var type = iterator.tokenType
        if (type == MatlabTypes.IDENTIFIER) {
            type = when (chars.subSequence(iterator.start, iterator.end).toString()) {
                "properties" -> MatlabElementTypes.PROPERTIES
                "methods" -> MatlabElementTypes.METHODS
                "events" -> MatlabElementTypes.EVENTS
                "enumeration" -> MatlabElementTypes.ENUMERATION
                else -> type
            }
        }
        return type
    }

    override fun toString(): String {
        return if (iterator.atEnd()) "atEnd" else iterator.tokenType.toString() + "=>" + chars.subSequence(startOffset(), Integer.min(startOffset() + 255, chars.length))
    }
}