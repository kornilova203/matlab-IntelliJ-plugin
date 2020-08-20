package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.*
import com.intellij.psi.ElementDescriptionLocation
import com.intellij.psi.ElementDescriptionProvider
import com.intellij.psi.PsiElement
import com.intellij.usageView.UsageViewTypeLocation

class MatlabElementDescriptionProvider: ElementDescriptionProvider {
    override fun getElementDescription(element: PsiElement, location: ElementDescriptionLocation): String? {
        if (location is UsageViewTypeLocation) {
            return when (element) {
                is MatlabFunctionDeclaration -> "function"
                is MatlabParameter -> "parameter"
                is MatlabRetValue -> "return value"
                is MatlabGlobalVariableDeclaration -> "global variable"
                is MatlabClassDeclaration -> "class"
                else -> null
            }
        }
        return null
    }
}