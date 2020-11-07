package com.github.kornilova203.matlab.inspections

import com.github.kornilova203.matlab.psi.*
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiReference
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.util.elementType
import com.intellij.psi.util.parentOfTypes

class MatlabUnusedVariableInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean) = MatlabUnusedVariableInspectionVisitor(holder)
}

class MatlabUnusedVariableInspectionVisitor(private val holder: ProblemsHolder) : MatlabVisitor() {
    override fun visitAssignExpr(expr: MatlabAssignExpr) {
        val firstChild = expr.left.firstChild
        if (expr.left is MatlabLiteralExpr && firstChild is MatlabMatrixLiteral) {
            registerMatrixItems(firstChild)
        } else {
            if (expr is MatlabDeclaration && isMustBeChecked(expr.left)) {
                registerProblem(expr, "variable") { deleteExpr(expr) }
            }
        }
    }

    override fun visitParameter(parameter: MatlabParameter) {
        val function = parameter.parentOfTypes(MatlabFunctionDeclaration::class)
        val retValues = function?.returnValues?.retValueList
        if (retValues != null) {
            for (retValue in retValues) {
                if (retValue.text == parameter.text) {
                    registerProblem(retValue as? MatlabDeclaration, "parameter", parameter) { deleteElementInList(parameter, MatlabTypes.COMMA) }
                    return
                }
            }
        }
        registerProblem(parameter as? MatlabDeclaration, "parameter") { deleteElementInList(parameter, MatlabTypes.COMMA) }
    }

    override fun visitCatchBlock(block: MatlabCatchBlock) {
        val exception = (block as? MatlabDeclaration)?.identifyingElement ?: return
        registerProblem(block as? MatlabDeclaration, "exception") {
            trim(exception)
            exception.delete()
        }
    }

    override fun visitRetValue(retValue: MatlabRetValue) {
        val retValues = retValue.parent
        registerProblem(retValue as? MatlabDeclaration, "return value") {
            if (retValues.children.size == 1 && retValues.firstChild.elementType != MatlabTypes.LBRACKET) {
                deleteWhiteSpace(retValues.nextSibling)
                if (retValues.nextSibling.elementType == MatlabTypes.ASSIGN) {
                    retValues.nextSibling.delete()
                    deleteWhiteSpace(retValues.nextSibling)
                }
                retValues.delete()
            } else {
                deleteElementInList(retValue, MatlabTypes.COMMA)
            }
        }
    }

    private fun registerMatrixItems(matrix: MatlabMatrixLiteral) {
        for (row in matrix.matrixRowList) {
            for (item in row.matrixItemList) {
                if ((item as? MatlabDeclaration)?.identifyingElement != null && isMustBeChecked(item)) {
                    registerProblem(item as? MatlabDeclaration, "variable") {
                        deleteElementInList(item, MatlabTypes.COMMA)
                    }
                }
            }
        }
    }
    
    private fun registerProblem(declaration: MatlabDeclaration?, name: String, invoke: (PsiElement) -> Unit) {
        registerProblem(declaration, name, null, invoke)
    }

    private fun registerProblem(declaration: MatlabDeclaration?, name: String, selectedElement: PsiElement?, invoke: (PsiElement) -> Unit) {
        declaration ?: return
        val refs = getReferences(declaration)
        if (!refs.isEmpty()) return
        holder.registerProblem(selectedElement ?: declaration.identifyingElement ?: declaration,
                "${name.capitalize()} '${declaration.name}' is never used",
                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                object : LocalQuickFixOnPsiElement(declaration) {
                    override fun getFamilyName(): String = "Remove $name"
                    override fun getText(): String = "Remove $name '${declaration.name}'"
                    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
                        invoke(declaration)
                    }
                }
        )
    }

    private fun isMustBeChecked(element: PsiElement): Boolean {
        val function = element.parentOfTypes(MatlabFunctionDeclaration::class)
        val retValues = function?.returnValues?.retValueList?.map { it.text } ?: emptyList()
        if (element is MatlabQualifiedExpr || element is MatlabFunctionExpr || retValues.contains(element.text) 
                || element.parentOfTypes(MatlabPropertiesBlock::class, MatlabForLoopRange::class, MatlabParforLoopRange::class) != null) {
            return false
        }
        val block = element.parentOfTypes(MatlabBlock::class) ?: return true
        return block.parent is MatlabFunctionDeclaration
    }

    private fun getReferences(element: PsiElement): Collection<PsiReference> {
        val scope = GlobalSearchScope.fileScope(element.containingFile)
        val query = ReferencesSearch.search(element, scope)
        val refs = query.findAll()
        if (element is MatlabAssignExpr) {
            return refs.filter { ref -> ref.element != element.left }
        }
        return refs
    }
}

