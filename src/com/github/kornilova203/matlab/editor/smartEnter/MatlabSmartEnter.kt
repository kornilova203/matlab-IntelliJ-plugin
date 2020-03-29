package com.github.kornilova203.matlab.editor.smartEnter

import com.intellij.codeInsight.editorActions.smartEnter.SmartEnterProcessor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

class MatlabSmartEnter : SmartEnterProcessor() {

  private val processors: List<SmartEnterProcessor> =
      listOf(
          StatementCompletionProcessor()
      )

  override fun process(project: Project, editor: Editor, psiFile: PsiFile): Boolean =
      processors.map { processor ->
        processor.process(project, editor, psiFile)
      }.reduce { didProcessStatement, wasProcessed ->
        didProcessStatement || wasProcessed
      }
}