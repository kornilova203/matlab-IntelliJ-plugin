package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.MatlabTypes
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.highlighter.HighlighterIterator
import com.intellij.psi.tree.IElementType
import com.intellij.util.text.CharArrayUtil

class MatlabSemanticEditorPosition(editor: EditorEx, offset: Int) {
    private var myIterator: HighlighterIterator? = null
    private var myEditor: EditorEx = editor
    private var myChars: CharSequence

    init {
        myIterator = editor.highlighter.createIterator(offset)
        myChars = myEditor.document.charsSequence
    }

    fun isAtEnd(): Boolean {
        return myIterator!!.atEnd()
    }

    fun isAt(element: IElementType) : Boolean {
        if (!myIterator!!.atEnd()) {
            val type = getType()
            return type == element
        }
        return false
    }

    fun isAtAnyOf(vararg elements: IElementType?): Boolean {
        if (!myIterator!!.atEnd()) {
            val type = getType()
            for (element in elements) {
                if (type == element) return true
            }
        }
        return false
    }

    fun isAtMultiline(): Boolean {
        return if (!myIterator!!.atEnd()) {
            CharArrayUtil.containLineBreaks(myChars, myIterator!!.start, myIterator!!.end)
        } else false
    }

    fun startOffset(): Int {
        return myIterator!!.start
    }

    fun moveAfter() {
        if (!myIterator!!.atEnd()) {
            myIterator!!.advance()
        }
    }

    fun after(): MatlabSemanticEditorPosition {
        return copyAnd { position: MatlabSemanticEditorPosition -> position.moveAfter() }
    }

    fun moveBeforeOptionalMix(vararg elements: IElementType?) {
        while (isAtAnyOf(*elements)) {
            myIterator!!.retreat()
        }
    }

    fun beforeOptionalMix(vararg elements: IElementType): MatlabSemanticEditorPosition {
        return copyAnd { position: MatlabSemanticEditorPosition -> position.moveBeforeOptionalMix(*elements)}
    }

    fun moveAfterOptionalMix(vararg elements: IElementType?) {
        while (isAtAnyOf(*elements)) {
            myIterator!!.advance()
        }
    }

    fun afterOptionalMix(vararg elements: IElementType): MatlabSemanticEditorPosition {
        return copyAnd { position: MatlabSemanticEditorPosition -> position.moveAfterOptionalMix(*elements) }
    }

    fun moveToLeftParenthesisBackwardsSkippingNested(leftParenthesis: Collection<IElementType>, rightParenthesis: Collection<IElementType>) {
        while (!myIterator!!.atEnd()) {
            val type = getType()
            if (rightParenthesis.contains(type)) {
                moveBeforeParentheses(leftParenthesis, rightParenthesis)
                continue
            } else if (leftParenthesis.contains(type)) {
                break
            }
            myIterator!!.retreat()
        }
    }

    fun moveBeforeParentheses(leftParenthesis: Collection<IElementType>, rightParenthesis: Collection<IElementType>) {
        var parenLevel = 0
        while (!myIterator!!.atEnd()) {
            val type = getType()
            myIterator!!.retreat()
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

    fun elementAfterOnSameLine(vararg elements: IElementType): IElementType? {
        myIterator!!.retreat()
        while (!myIterator!!.atEnd() && !isAtMultiline()) {
            val type = getType()
            for (element in elements) {
                if (type == element) return element
            }
            myIterator!!.retreat()
        }
        return null
    }

    fun copyAnd(modifier: (MatlabSemanticEditorPosition) -> Unit): MatlabSemanticEditorPosition {
        val position: MatlabSemanticEditorPosition = copy()
        modifier(position)
        return position
    }

    fun copy(): MatlabSemanticEditorPosition {
        return MatlabSemanticEditorPosition(myEditor, if (isAtEnd()) -1 else myIterator!!.start)
    }

    fun getType() : IElementType {
        var type = myIterator!!.tokenType
        if (type == MatlabTypes.IDENTIFIER) {
            type = when (myChars.subSequence(myIterator!!.start, myIterator!!.end).toString()) {
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
        return if (myIterator!!.atEnd()) "atEnd" else myIterator!!.tokenType.toString() + "=>" + myChars.subSequence(startOffset(), Integer.min(startOffset() + 255, myChars.length))
    }
}