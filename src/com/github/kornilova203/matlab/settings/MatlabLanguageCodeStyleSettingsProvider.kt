package com.github.kornilova203.matlab.settings

import com.github.kornilova203.matlab.MatlabLanguage
import com.intellij.application.options.IndentOptionsEditor
import com.intellij.application.options.SmartIndentOptionsEditor
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider

class MatlabLanguageCodeStyleSettingsProvider(): LanguageCodeStyleSettingsProvider() {

    override fun getLanguage(): Language {
        return MatlabLanguage.INSTANCE
    }

    override fun getIndentOptionsEditor(): IndentOptionsEditor {
        return SmartIndentOptionsEditor()
    }

    override fun getCodeSample(settingsType: SettingsType): String {
        return """
            function sum = add(a, b)
                sum = a + b;
            end
        """.trimIndent()
    }

}
