package com.github.korniloval.matlab.execution

import com.github.korniloval.matlab.psi.MatlabFile
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.RunConfigurationProducer
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement

class MatlabRunConfigurationProducer
    : RunConfigurationProducer<MatlabRunConfiguration>(MatlabConfigurationType.getInstance()) {

    override fun isConfigurationFromContext(configuration: MatlabRunConfiguration?, context: ConfigurationContext?): Boolean {
        if (configuration != null && context != null) {
            val matlabFile = context.location?.psiElement
            if (matlabFile is MatlabFile) {
                return configuration.getFilePath() == matlabFile.virtualFile.canonicalPath
            }
        }
        return false
    }

    override fun setupConfigurationFromContext(configuration: MatlabRunConfiguration?, context: ConfigurationContext?, sourceElement: Ref<PsiElement>?): Boolean {
        if (sourceElement != null && configuration != null) {
            val matlabFile = sourceElement.get()
            if (matlabFile is MatlabFile) {
                val path = matlabFile.virtualFile.canonicalPath
                if (path != null) {
                    configuration.setFilePath(path)
                    configuration.name = matlabFile.virtualFile.name
                    return true
                }
            }
        }
        return false
    }
}
