package com.github.kornilova_l.matlab.execution

import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.util.ThrowableRunnable
import junit.framework.TestCase

class RunConfigurationTest : RunConfigurationTestCase() {

    private val fileContent = """
                |function [] = hello()
                |    fprintf("hello, world\n")
                |end
                """.trimMargin()

    private val interpreterPath = "/usr/local/bin/octave"

    fun testRunConfigurationFromFile() {
        val myFile = myFixture.configureByText("hello.m", fileContent)
        val configuration = createConfiguration(myFile)

        TestCase.assertNotNull(configuration)
        configuration ?: return
        TestCase.assertNull(configuration.getInterpreterPath())
        TestCase.assertEquals(myFile.virtualFile.canonicalPath, configuration.getFilePath())
    }

    fun testSetInterpreterPath() {
        val myFile = myFixture.configureByText("hello.m", fileContent)
        val configuration = createConfiguration(myFile)

        TestCase.assertNotNull(configuration)
        configuration ?: return

        TestCase.assertNull(configuration.getInterpreterPath())

        configuration.setInterpreterPath(interpreterPath)

        assertInterpreterPath(createConfiguration(myFile), interpreterPath)

        val myFile2 = myFixture.configureByText("hello2.m", fileContent)
        assertInterpreterPath(createConfiguration(myFile2), interpreterPath)
    }

    fun testRunnerExceptions() {
        val myFile = myFixture.configureByText("hello.m", fileContent)
        val configuration = createConfiguration(myFile)!!

        val executor = DefaultRunExecutor.getRunExecutorInstance()
        val executionEnvironment = ExecutionEnvironment()
        assertThrows(MatlabCannotRunException::class.java,
                "Matlab interpreter is not specified.",
                ThrowableRunnable<MatlabCannotRunException> { configuration.getState(executor, executionEnvironment) })

        configuration.setInterpreterPath(interpreterPath)
    }
}
