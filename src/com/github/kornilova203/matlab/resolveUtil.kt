package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.MatlabDeclaration
import com.github.kornilova203.matlab.psi.MatlabFunctionDeclaration
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

fun PsiElement.forEachSiblingBackwards(processor: (PsiElement) -> Boolean): Boolean {
    var el: PsiElement? = this.prevSibling
    while (el != null) {
        if (!processor(el)) return false
        el = el.prevSibling
    }
    return true
}

fun PsiElement.forEachSiblingForward(processor: (PsiElement) -> Boolean): Boolean {
    var el: PsiElement? = this.nextSibling
    while (el != null) {
        if (!processor(el)) return false
        el = el.nextSibling
    }
    return true
}

fun PsiElement.inCurrentFunction(parent: PsiElement): Boolean {
    var el: PsiElement = this
    while (el != parent) {
        if (el is MatlabFunctionDeclaration) return false
        el = el.parent
    }
    return true
}

fun processDeclarations(parent: PsiElement, processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
    val inCurrentScope = place.inCurrentFunction(parent)

    val res = lastParent?.forEachSiblingBackwards { el ->
        if (el is MatlabDeclaration) {
            if (inCurrentScope || el.visibleOutsideFunction) {
                return@forEachSiblingBackwards processor.execute(el, state)
            }
        }
        return@forEachSiblingBackwards true
    }
    if (res == false) return false

    if (inCurrentScope) return true

    return lastParent?.forEachSiblingForward { el ->
        if (el is MatlabDeclaration && el.visibleOutsideFunction) {
            return@forEachSiblingForward processor.execute(el, state)
        }
        return@forEachSiblingForward true
    } ?: true
}