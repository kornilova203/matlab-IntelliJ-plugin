package com.github.kornilova203.matlab

import com.github.kornilova203.matlab.psi.*
import com.github.kornilova203.matlab.psi.types.MatlabTypeClass
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.util.PsiTreeUtil

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

fun PsiElement.findDeclaration(processor: PsiScopeProcessor, state: ResolveState, inCurrentScope: Boolean, declarationIsBeforeUsage: Boolean = true): Boolean {
    if (this is MatlabDeclaration) {
        if ((inCurrentScope || this.visibleOutsideFunction) && (declarationIsBeforeUsage || this.visibleBeforeDeclaration)) {
            return processor.execute(this, state)
        }
    }
    return true
}

fun processDeclarations(parent: PsiElement, processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
    val inCurrentScope = place.inCurrentFunction(parent)

    if (lastParent != null && lastParent !is MatlabAssignExpr) {
        if (!lastParent.findDeclaration(processor, state, inCurrentScope)) {
            return false
        }
    }

    val res = lastParent?.forEachSiblingBackwards { it.findDeclaration(processor, state, inCurrentScope) }
    if (res == false) return false

    return lastParent?.forEachSiblingForward { it.findDeclaration(processor, state, inCurrentScope, false) } ?: true
}

fun processDeclarationsInClass(processor: PsiScopeProcessor, state: ResolveState, type: MatlabTypeClass): Boolean {
    val classDeclaration = type.decl
    val declarations = PsiTreeUtil.findChildrenOfAnyType(classDeclaration, false,
            MatlabProperty::class.java,
            MatlabFunctionDeclaration::class.java,
            MatlabEnumItem::class.java)

    for (declaration in declarations) {
        if (!processor.execute(declaration, state)) {
            break
        }
    }
    return false
}