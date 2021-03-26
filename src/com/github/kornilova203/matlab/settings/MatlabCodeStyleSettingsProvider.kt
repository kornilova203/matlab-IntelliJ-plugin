package com.github.kornilova203.matlab.settings

import com.github.kornilova203.matlab.MatlabLanguage
import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.*

class MatlabCodeStyleSettingsProvider: CodeStyleSettingsProvider() {

    override fun createCustomSettings(settings: CodeStyleSettings?): CustomCodeStyleSettings {
        return MatlabCodeStyleSettings(settings)
    }

    override fun createConfigurable(
        settings: CodeStyleSettings,
        modelSettings: CodeStyleSettings
    ): CodeStyleConfigurable {
        return object: CodeStyleAbstractConfigurable(settings, modelSettings, configurableDisplayName) {
            override fun createPanel(settings: CodeStyleSettings?): CodeStyleAbstractPanel {
                return MatlabCodeStyleMainPanel(currentSettings, settings)
            }
        }
    }

    override fun getConfigurableDisplayName(): String {
        return "Matlab"
    }

    override fun getLanguage(): Language {
        return MatlabLanguage.INSTANCE
    }

    class MatlabCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings?):
        TabbedLanguageCodeStylePanel(MatlabLanguage.INSTANCE, currentSettings, settings)

}
