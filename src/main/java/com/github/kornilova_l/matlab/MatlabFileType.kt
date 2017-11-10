package com.github.kornilova_l.matlab

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class MatlabFileType(language: Language) : LanguageFileType(language) {
    override fun getIcon(): Icon? {
        return null
    }

    override fun getName(): String {
        return "matlab"
    }

    override fun getDefaultExtension(): String {
        return "m"
    }

    override fun getDescription(): String {
        return ""
    }

}