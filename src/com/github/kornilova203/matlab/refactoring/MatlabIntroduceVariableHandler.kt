package com.github.kornilova203.matlab.refactoring

import com.github.kornilova203.matlab.psi.*
import com.intellij.codeInsight.PsiEquivalenceUtil
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.RangeMarker
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Pass
import com.intellij.psi.*
import com.intellij.psi.search.SearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.psi.util.parentOfTypes
import com.intellij.refactoring.IntroduceTargetChooser
import com.intellij.refactoring.RefactoringActionHandler
import com.intellij.refactoring.introduce.inplace.InplaceVariableIntroducer
import com.intellij.refactoring.introduce.inplace.OccurrencesChooser
import com.intellij.refactoring.introduce.inplace.OccurrencesChooser.ReplaceChoice
import com.intellij.refactoring.suggested.startOffset
import com.intellij.refactoring.util.CommonRefactoringUtil

class MatlabIntroduceVariableHandler : RefactoringActionHandler {
    override fun invoke(project: Project, editor: Editor?, file: PsiFile?, dataContext: DataContext?) {
        if (editor == null || file == null) {
            showCannotPerformError(project, editor)
            return
        }
        val selectionModel = editor.selectionModel
        if (selectionModel.hasSelection()) {
            val selectedElement = getSelectedElement(editor, file)
            extractElement(selectedElement, project, editor, file)
        } else {
            if (!smartExtract(project, editor, file)) {
                showCannotPerformError(project, editor)
                return
            }
        }
    }

    override fun invoke(project: Project, elements: Array<out PsiElement>, dataContext: DataContext?) {
    }

    private fun getSelectedElement(editor: Editor, file: PsiFile): PsiElement? {
        val selectionModel = editor.selectionModel
        var firstElement = file.findElementAt(selectionModel.selectionStart)
        var lastElement = file.findElementAt(selectionModel.selectionEnd - 1)
        if (firstElement is PsiWhiteSpace) {
            val startOffset = firstElement.getTextRange().endOffset
            firstElement = file.findElementAt(startOffset)
        }
        if (lastElement is PsiWhiteSpace) {
            val endOffset = lastElement.getTextRange().startOffset
            lastElement = file.findElementAt(endOffset - 1)
        }
        if (firstElement == null || lastElement == null) {
            return null
        }

        if (firstElement == lastElement) {
            return if (isValidExtractVariant(firstElement.parent)) firstElement.parent else null
        }

        val parent = PsiTreeUtil.findCommonParent(firstElement, lastElement) ?: return null
        if (firstElement == PsiTreeUtil.getDeepestFirst(parent) && lastElement == PsiTreeUtil.getDeepestLast(parent) && isValidExtractVariant(parent)) {
            return parent
        }
        return null
    }

    private fun isValidExtractVariant(element: PsiElement): Boolean {
        if (element.parent is MatlabAssignExpr && (element.parent as MatlabAssignExpr).left == element) {
            return false
        }
        return element is MatlabExpr && element !is MatlabAssignExpr && !isAfterDot(element)
    }

    private fun isAfterDot(element: PsiElement): Boolean {
        val file = element.containingFile
        return file.findElementAt(element.startOffset - 1).elementType == MatlabTypes.DOT
    }

    private fun smartExtract(project: Project, editor: Editor, file: PsiFile): Boolean {
        val offset = editor.caretModel.offset
        var element = file.findElementAt(offset)
        if (element == null || element is PsiWhiteSpace || element.elementType == MatlabTypes.NEWLINE) {
            element = file.findElementAt(offset - 1)
        }
        val variants = ArrayList<PsiElement>()
        while (element != null && element !is MatlabFile && element !is MatlabBlock) {
            if (isValidExtractVariant(element) && element.parent !is MatlabParenExpr && element.parent !is MatlabFunctionExpr) {
                variants.add(element)
            }
            element = element.parent
        }
        if (variants.size == 0) return false
        if (variants.size == 1 || ApplicationManager.getApplication().isUnitTestMode) {
            extractElement(variants[0], project, editor, file)
            return true
        }
        IntroduceTargetChooser.showChooser(editor, variants, object : Pass<PsiElement?>() {
                override fun pass(element: PsiElement?) {
                    extractElement(element, project, editor, file)
                }
            }) { psiElement -> psiElement.text }
        return true
    }

    private fun extractElement(extractedElement: PsiElement?, project: Project, editor: Editor, file: PsiFile) {
        if (extractedElement == null) {
            showCannotPerformError(project, editor)
            return
        }
        val occurrences = findOccurrences(extractedElement)
        if (occurrences.size > 1 && !ApplicationManager.getApplication().isUnitTestMode) {
            OccurrencesChooser.simpleChooser<PsiElement>(editor).showChooser(extractedElement, occurrences, object : Pass<ReplaceChoice>() {
                override fun pass(replaceChoice: ReplaceChoice) {
                    val toReplace = if (replaceChoice == ReplaceChoice.ALL) occurrences else listOf(extractedElement)
                    refactorDocument(extractedElement, toReplace, project, editor, file)
                }
            })
        } else {
            refactorDocument(extractedElement, occurrences, project, editor, file)
        }
    }

    private fun findOccurrences(extractedElement: PsiElement): List<PsiElement> {
        val parent = extractedElement.parentOfTypes(MatlabFunctionDeclaration::class, MatlabFile::class)
                ?: return emptyList()
        val occurrences = ArrayList<PsiElement>()
        val visitor = object : PsiRecursiveElementVisitor() {
            override fun visitElement(element: PsiElement) {
                if (element is MatlabFunctionDeclaration) {
                    return
                }
                if (PsiEquivalenceUtil.areElementsEquivalent(extractedElement, element) && !isAfterDot(element)) {
                    occurrences.add(element)
                } else {
                    super.visitElement(element)
                }
            }
        }
        parent.acceptChildren(visitor)
        return occurrences
    }

    private fun refactorDocument(extractedElement: PsiElement, occurrences: List<PsiElement>, project: Project, editor: Editor, file: PsiFile) {
        val firstElement = findStart(extractedElement, occurrences)
        val start = firstElement.startOffset
        val declarationText = "x = " + getText(extractedElement) + "\n" + getIndent(firstElement, editor, file)
        val document = editor.document
        val markers = ArrayList<RangeMarker>()
        occurrences.forEach { occurrence ->
            if (occurrence.parent is MatlabParenExpr) {
                markers.add(document.createRangeMarker(occurrence.parent.textRange))
            } else {
                markers.add(document.createRangeMarker(occurrence.textRange))
            }
        }
        WriteCommandAction.runWriteCommandAction(project) {
            document.insertString(start, declarationText)
            markers.forEach { marker -> document.replaceString(marker.startOffset, marker.endOffset, "x") }
            PsiDocumentManager.getInstance(project).commitDocument(document)
        }
        editor.caretModel.moveToOffset(start)
        val declaration = findReference(start, file)!!
        val refs = hashSetOf(declaration.reference!!)
        markers.forEach { marker ->
            val ref = findReference(marker.startOffset, file)!!
            refs.add(ref.reference!!)
        }
        MatlabInplaceVariableIntroducer(declaration, editor, project, "Introduce Variable", refs).performInplaceRefactoring(null)
    }

    private fun findStart(extractedElement: PsiElement, occurrences: List<PsiElement>): PsiElement {
        var element = extractedElement
        occurrences.forEach { occurrence ->
            if (occurrence.startOffset < element.startOffset) {
                element = occurrence
            }
        }
        while (element.parent !is MatlabBlock && element.parent !is MatlabFile) {
            element = element.parent
        }
        return element
    }

    private fun findReference(offset: Int, file: PsiFile): MatlabRefExpr? {
        var element = file.findElementAt(offset)
        while (element != null && element !is MatlabRefExpr) {
            element = element.parent
        }
        return element as MatlabRefExpr
    }

    private fun getText(element: PsiElement) : String {
        val text = element.text
        if (element is MatlabParenExpr) {
            return text.substring(1, text.length - 1)
        }
        return text
    }

    private fun getIndent(element: PsiElement, editor: Editor, file: PsiFile): String {
        val document = editor.document
        val start = document.getLineStartOffset(document.getLineNumber(element.startOffset))
        val startElement = file.findElementAt(start)
        if (startElement is PsiWhiteSpace) {
            return startElement.text
        }
        return ""
    }

    private fun showCannotPerformError(project: Project, editor: Editor?) {
        CommonRefactoringUtil.showErrorHint(project, editor, "Cannot find expression to introduce", "Extract Variable", "refactoring.introduceVariable")
    }

    private class MatlabInplaceVariableIntroducer(val declaration: MatlabRefExpr,
                                                  editor: Editor,
                                                  project: Project,
                                                  title: String,
                                                  val refs: MutableCollection<PsiReference>)
        : InplaceVariableIntroducer<PsiElement>(declaration.parent as MatlabDeclaration, editor, project, title, emptyArray(), null) {

        override fun collectRefs(referencesSearchScope: SearchScope?): MutableCollection<PsiReference> {
            return refs
        }

        override fun checkLocalScope(): PsiElement? {
            return declaration.containingFile
        }
    }
}
