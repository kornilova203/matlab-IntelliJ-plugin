package com.github.korniloval.matlab.execution

import com.github.korniloval.matlab.psi.MatlabFile
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.RunConfigurationProducer
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement

class MatlabRunConfigurationProducer
    : RunConfigurationProducer<MatlabRunConfiguration>(MatlabConfigurationType.getInstance()) {

    override fun isConfigurationFromContext(configuration: MatlabRunConfiguration, context: ConfigurationContext): Boolean {
        val matlabFile = context.location?.psiElement as? MatlabFile ?: return false
        val path = matlabFile.virtualFile.canonicalPath ?: return false
        return configuration.getCommand() == configuration.guessExecutionHelper().runFileCommand(path)
    }

    override fun setupConfigurationFromContext(configuration: MatlabRunConfiguration, context: ConfigurationContext, sourceElement: Ref<PsiElement>): Boolean {
        val matlabFile = sourceElement.get() as? MatlabFile ?: return false
        val path = matlabFile.virtualFile.canonicalPath ?: return false

        val helper = configuration.guessExecutionHelper()
        configuration.setCommand(helper.runFileCommand(path))
        configuration.name = matlabFile.virtualFile.name
        return true
    }
}
