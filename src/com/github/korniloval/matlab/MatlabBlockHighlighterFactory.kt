package com.github.korniloval.matlab

import com.github.korniloval.matlab.psi.*
import com.github.korniloval.matlab.psi.MatlabTypes.*
import com.intellij.codeHighlighting.Pass
import com.intellij.codeHighlighting.TextEditorHighlightingPass
import com.intellij.codeHighlighting.TextEditorHighlightingPassFactory
import com.intellij.codeHighlighting.TextEditorHighlightingPassRegistrar
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.codeInsight.daemon.impl.HighlightInfoType
import com.intellij.codeInsight.daemon.impl.UpdateHighlightersUtil
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.components.NamedComponent
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.CodeInsightColors
import com.intellij.openapi.editor.colors.EditorColors
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType.WHITE_SPACE
import com.intellij.psi.impl.source.tree.CompositeElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.PsiUtilCore
import com.intellij.util.containers.ContainerUtil

/**
 * @author Liudmila Kornilova
 **/
class MatlabBlockHighlighterFactory(registrar: TextEditorHighlightingPassRegistrar) : TextEditorHighlightingPassFactory, BaseComponent, ProjectComponent, NamedComponent {
    init {
        registrar.registerTextEditorHighlightingPass(this, null, intArrayOf(Pass.UPDATE_ALL), false, -1)
    }

    override fun createHighlightingPass(file: PsiFile, editor: Editor): MatlabBlockHighlightingPass? {
        return if (file is MatlabFile) MatlabBlockHighlightingPass(file, editor) else null
    }

    class MatlabBlockHighlightingPass(private val file: PsiFile, private val editor: Editor) : TextEditorHighlightingPass(file.project, editor.document, false) {
        private val ranges = ContainerUtil.newArrayList<TextRange>()
        private var isMatched = false

        override fun doCollectInformation(progress: ProgressIndicator) {
            findPair(editor.caretModel.offset)
        }

        private fun findPair(offset: Int) {
            ranges.clear()
            var element = file.findElementAt(offset) ?: return
            val type = PsiUtilCore.getElementType(element)
            if (offset > 0 && (type == NEWLINE || type == WHITE_SPACE)) element = file.findElementAt(offset - 1) ?: return
            element.parent.accept(MyVisitor(element))
        }

        override fun doApplyInformationToEditor() {
            val infos: List<HighlightInfo>
            if (ranges.isEmpty()) infos = emptyList()
            else {
                infos = ContainerUtil.newArrayList()
                val type = if (ranges.size > 1 || isMatched) MATCHED else UNMATCHED
                for (range in ranges) {
                    ContainerUtil.addIfNotNull(infos, HighlightInfo.newHighlightInfo(type).range(range).create())
                }
            }
            UpdateHighlightersUtil.setHighlightersToEditor(
                    file.project, editor.document, 0, file.textLength,
                    infos, colorsScheme, id)
        }

        inner class MyVisitor(private val element: PsiElement) : MatlabVisitor() {
            private fun getFirstAndEnd(o: PsiElement): MutableList<PsiElement> {
                return if (PsiUtilCore.getElementType(o.lastChild) == END) mutableListOf(o.firstChild, o.lastChild) else mutableListOf(o.firstChild)
            }

            private fun possiblyAddRanges(delimiters: List<PsiElement>) {
                if (!delimiters.contains(element)) return
                delimiters.mapTo(ranges) { it.textRange }
            }

            private fun highlightFirstAndEnd(o: PsiElement) {
                val delimiters = getFirstAndEnd(o)
                possiblyAddRanges(delimiters)
            }

            override fun visitIfBlock(o: MatlabIfBlock) {
                super.visitIfBlock(o)
                val delimiters = getFirstAndEnd(o)
                addElementsInChildren(o, delimiters, MatlabElseBlock::class.java, ELSE)
                addElementsInChildren(o, delimiters, MatlabElseifBlock::class.java, ELSEIF)
                possiblyAddRanges(delimiters)
            }

            override fun visitTryBlock(o: MatlabTryBlock) {
                super.visitTryBlock(o)
                val delimiters = getFirstAndEnd(o)
                addElementsInChildren(o, delimiters, MatlabCatchBlock::class.java, CATCH)
                possiblyAddRanges(delimiters)
            }

            override fun visitCatchBlock(o: MatlabCatchBlock) {
                super.visitCatchBlock(o)
                if (o.parent is MatlabTryBlock) o.parent.accept(this)
            }

            private fun <T : PsiElement> addElementsInChildren(o: PsiElement, delimiters: MutableList<PsiElement>, clazz: Class<T>, type: IElementType) {
                PsiTreeUtil.getChildrenOfType(o, clazz)?.forEach { child ->
                    (child as? CompositeElement)?.findPsiChildByType(type)?.let { delimiters.add(it) }
                }
            }

            override fun visitElseBlock(o: MatlabElseBlock) {
                super.visitElseBlock(o)
                if (o.parent is MatlabIfBlock) o.parent.accept(this)
            }

            override fun visitElseifBlock(o: MatlabElseifBlock) {
                super.visitElseifBlock(o)
                if (o.parent is MatlabIfBlock) o.parent.accept(this)
            }

            override fun visitFunctionDeclaration(o: MatlabFunctionDeclaration) {
                highlightFirstAndEnd(o)
                isMatched = true
            }

            override fun visitMethodsBlock(o: MatlabMethodsBlock) = highlightFirstAndEnd(o)
            override fun visitPropertiesBlock(o: MatlabPropertiesBlock) = highlightFirstAndEnd(o)
            override fun visitEventsBlock(o: MatlabEventsBlock) = highlightFirstAndEnd(o)
            override fun visitClassDeclaration(o: MatlabClassDeclaration) = highlightFirstAndEnd(o)
            override fun visitForLoop(o: MatlabForLoop) = highlightFirstAndEnd(o)
            override fun visitWhileLoop(o: MatlabWhileLoop) = highlightFirstAndEnd(o)
            override fun visitSwitchBlock(o: MatlabSwitchBlock) = highlightFirstAndEnd(o)

            override fun visitCaseBlock(o: MatlabCaseBlock) {
                val switch = o.parent as? MatlabSwitchBlock ?: return
                val delimiters = mutableListOf<PsiElement>()
                addElementsInChildren(switch, delimiters, MatlabCaseBlock::class.java, CASE)
                addElementsInChildren(switch, delimiters, MatlabOtherwiseBlock::class.java, OTHERWISE)
                possiblyAddRanges(delimiters)
                isMatched = true
            }
        }
    }

    companion object {
        private val MATCHED = HighlightInfoType.HighlightInfoTypeImpl(HighlightInfoType.SYMBOL_TYPE_SEVERITY, EditorColors.IDENTIFIER_UNDER_CARET_ATTRIBUTES)
        private val UNMATCHED = HighlightInfoType.HighlightInfoTypeImpl(HighlightSeverity.ERROR, CodeInsightColors.UNMATCHED_BRACE_ATTRIBUTES)
    }

    override fun initComponent() {}
    override fun disposeComponent() {}
    override fun projectClosed() {}
    override fun projectOpened() {}
    override fun getComponentName(): String = javaClass.name
}
