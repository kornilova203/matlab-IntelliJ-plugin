package com.github.kornilova203.matlab.settings

import com.github.kornilova203.matlab.MatlabLanguage
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleSettings

class MatlabCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings?):
    TabbedLanguageCodeStylePanel(MatlabLanguage.INSTANCE, currentSettings, settings) {

    override fun initTabs(settings: CodeStyleSettings?) {
        addIndentOptionsTab(settings)
    }
}
