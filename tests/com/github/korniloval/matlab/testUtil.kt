package com.github.korniloval.matlab

import com.intellij.testFramework.TestDataPath
import java.nio.file.Paths

fun getTestDataRoot(clazz: Class<*>): String {
    val annotation = clazz.getAnnotation(TestDataPath::class.java)!!
    return Paths.get("testData", annotation.value).toAbsolutePath().toString()
}
