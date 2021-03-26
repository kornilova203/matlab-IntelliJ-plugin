package com.github.kornilova203.matlab.settings

import com.github.kornilova203.matlab.MatlabLanguage
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider

class MatlabLanguageCodeStyleSettingsProvider(): LanguageCodeStyleSettingsProvider() {

    override fun getLanguage(): Language {
        return MatlabLanguage.INSTANCE
    }

    override fun getCodeSample(settingsType: SettingsType): String {
        return """
            function result = sum(a, b)
                result = a + b;
            end
        """.trimIndent()
    }

}
