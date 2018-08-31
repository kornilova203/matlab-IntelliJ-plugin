package com.github.korniloval.matlab

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class MatlabFileType private constructor() : LanguageFileType(MatlabLanguage.INSTANCE) {

    companion object {
        val INSTANCE = MatlabFileType()
    }

    override fun getIcon(): Icon? {
        return Icons.matlabIcon
    }

    override fun getName(): String {
        return "Matlab"
    }

    override fun getDefaultExtension(): String {
        return "m"
    }

    override fun getDescription(): String {
        return "Matlab language file"
    }

}