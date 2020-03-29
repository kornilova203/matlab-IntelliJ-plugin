package com.github.kornilova203.matlab.execution

import com.github.kornilova203.matlab.psi.MatlabFile
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.LazyRunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement

class MatlabRunConfigurationProducer : LazyRunConfigurationProducer<MatlabRunConfiguration>() {

    override fun isConfigurationFromContext(configuration: MatlabRunConfiguration, context: ConfigurationContext): Boolean {
        val matlabFile = context.location?.psiElement
        if (matlabFile !is MatlabFile) return false
        return configuration.getFilePath() == matlabFile.virtualFile.canonicalPath
    }

    override fun setupConfigurationFromContext(configuration: MatlabRunConfiguration, context: ConfigurationContext, sourceElement: Ref<PsiElement>): Boolean {
        val matlabFile = sourceElement.get()
        if (matlabFile !is MatlabFile) return false
        val path = matlabFile.virtualFile.canonicalPath ?: return false
        configuration.setFilePath(path)
        configuration.name = matlabFile.virtualFile.name
        return true
    }

    override fun getConfigurationFactory(): ConfigurationFactory {
        return ConfigurationTypeUtil.findConfigurationType(MatlabConfigurationType::class.java)
    }
}
