package com.github.korniloval.matlab.execution

import com.intellij.execution.Executor
import com.intellij.execution.configuration.EnvironmentVariablesComponent
import com.intellij.execution.configurations.*
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.JDOMExternalizerUtil
import org.jdom.Element
import java.lang.Boolean.parseBoolean
import java.util.*

class MatlabRunConfiguration(project: Project, configurationFactory: ConfigurationFactory) :
        LocatableConfigurationBase<RunConfigurationOptions>(project, configurationFactory, "Matlab run configuration"), MatlabRunConfigurationsParams {

    private var workingDir: String? = null
    private var programParams: String? = null
    private var interpreterPath: String? = PropertiesComponent.getInstance(project).getValue(MATLAB_INTERPRETER) // null or non empty string
    private var path: String? = null // null or non empty string
    private var command: String? = null // null or non empty string
    private var interpreterOptions: String? = null // null or non empty string

    /* see AbstractRunConfiguration */
    private val myEnvs = LinkedHashMap<String, String>()
    private var myPassParentEnvs = true

    override fun getInterpreterPath(): String? = interpreterPath

    override fun setInterpreterPath(path: String) {
        if (path.isBlank()) {
            interpreterPath = null
        } else {
            interpreterPath = path
            /* update project level interpreter */
            PropertiesComponent.getInstance(project).setValue(MATLAB_INTERPRETER, path)
        }
    }

    override fun getPath(): String? = path

    override fun getCommand(): String? = command

    override fun setPath(path: String) {
        this.path = if (path.isBlank()) null else path
    }

    override fun setCommand(command: String) {
        this.command = if (command.isBlank()) null else command
    }

    override fun getInterpreterOptions(): String? = interpreterOptions

    override fun setInterpreterOptions(options: String) {
        interpreterOptions = if (options.isBlank()) null else options
    }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> = MatlabSettingsEditor()

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState? {
        if (interpreterPath == null) {
            throw MatlabCannotRunException.interpreterNotSetUp()
        }
        if (command == null) {
            throw MatlabCannotRunException("Command is not set")
        }
        return MatlabCommandLineState(this, environment)
    }

    override fun getWorkingDirectory(): String? = workingDir

    override fun setWorkingDirectory(dir: String?) {
        workingDir = dir
    }

    override fun setProgramParameters(params: String?) {
        programParams = params
    }

    override fun getProgramParameters(): String? = programParams

    override fun writeExternal(element: Element) {
        super.writeExternal(element)

        JDOMExternalizerUtil.writeField(element, "PATH", path)
        JDOMExternalizerUtil.writeField(element, "COMMAND", command)
        JDOMExternalizerUtil.writeField(element, "INTERPRETER_PATH", interpreterPath)
        JDOMExternalizerUtil.writeField(element, "WORKING_DIRECTORY", workingDirectory)
        JDOMExternalizerUtil.writeField(element, "PARENT_ENVS", isPassParentEnvs.toString())
        JDOMExternalizerUtil.writeField(element, "PARAMETERS", programParameters)
        JDOMExternalizerUtil.writeField(element, "INTERPRETER_OPTIONS", interpreterOptions)
        EnvironmentVariablesComponent.writeExternal(element, myEnvs)
    }

    override fun readExternal(element: Element) {
        super.readExternal(element)
        EnvironmentVariablesComponent.readExternal(element, myEnvs)

        path = JDOMExternalizerUtil.readField(element, "PATH")
        if (path.isNullOrBlank()) {
            path = null
        }
        command = JDOMExternalizerUtil.readField(element, "COMMAND")
        if (command.isNullOrBlank()) {
            command = null
        }
        interpreterPath = JDOMExternalizerUtil.readField(element, "INTERPRETER_PATH")
        if (interpreterPath.isNullOrBlank()) {
            interpreterPath = null
        }
        interpreterOptions = JDOMExternalizerUtil.readField(element, "INTERPRETER_OPTIONS")
        if (interpreterOptions.isNullOrBlank()) {
            interpreterOptions = null
        }
        workingDirectory = JDOMExternalizerUtil.readField(element, "WORKING_DIRECTORY")
        programParameters = JDOMExternalizerUtil.readField(element, "PARAMETERS")

        val parentEnvValue = JDOMExternalizerUtil.readField(element, "PARENT_ENVS")
        if (parentEnvValue != null) {
            isPassParentEnvs = parseBoolean(parentEnvValue)
        }
    }

    override fun getEnvs(): MutableMap<String, String> = myEnvs

    override fun setEnvs(envs: MutableMap<String, String>) {
        myEnvs.clear()
        myEnvs.putAll(envs)
    }

    override fun isPassParentEnvs(): Boolean = myPassParentEnvs

    override fun setPassParentEnvs(passParentEnvs: Boolean) {
        myPassParentEnvs = passParentEnvs
    }

    companion object {
        const val MATLAB_INTERPRETER = "MATLAB_INTERPRETER"
    }
}