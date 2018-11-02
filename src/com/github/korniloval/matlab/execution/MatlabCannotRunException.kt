package com.github.korniloval.matlab.execution

import com.intellij.execution.ExecutionException


class MatlabCannotRunException(message: String) : ExecutionException(message) {
    companion object {
        fun interpreterNotSetUp(): MatlabCannotRunException {
            return MatlabCannotRunException("Matlab interpreter is not specified.")
        }

        fun fileNotSetUp(): MatlabCannotRunException {
            return MatlabCannotRunException("File is not specified.")
        }
    }
}
