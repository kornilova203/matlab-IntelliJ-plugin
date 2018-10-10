package com.github.korniloval.matlab

import com.intellij.lang.Language

class MatlabLanguage private constructor() : Language("Matlab") {
    companion object {
        val INSTANCE = MatlabLanguage()
    }

    override fun isCaseSensitive(): Boolean = true
}