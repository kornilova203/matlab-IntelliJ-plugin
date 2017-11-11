package com.github.kornilova_l.matlab

import com.intellij.lang.Language

class MatlabLanguage private constructor() : Language("Matlab") {
    companion object {
        val INSTANCE = MatlabLanguage()
    }
}