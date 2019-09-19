package com.github.korniloval.matlab.execution

import com.intellij.openapi.util.text.StringUtil

sealed class CommandHelper {
    abstract fun runFileCommand(path: String): String
}

object OctaveCommandHelper : CommandHelper() {
    override fun runFileCommand(path: String) = "run(\"${StringUtil.escapeQuotes(path)}\")"
}

fun guessCommandHelper(isMatlab: Boolean) = OctaveCommandHelper
