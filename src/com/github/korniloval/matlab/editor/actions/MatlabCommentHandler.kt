package com.github.korniloval.matlab.editor.actions

import com.intellij.lang.Commenter

class MatlabCommentHandler : Commenter {
    override fun getCommentedBlockCommentSuffix(): String? {
        return null
    }

    override fun getBlockCommentPrefix(): String? {
        return "%{\n"
    }

    override fun getBlockCommentSuffix(): String? {
        return "\n%}"
    }

    override fun getLineCommentPrefix(): String? {
        return "%"
    }

    override fun getCommentedBlockCommentPrefix(): String? {
        return null
    }
}
