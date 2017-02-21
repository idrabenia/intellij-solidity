package me.serce.solidity.ide.actions

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiDirectory
import me.serce.solidity.ide.SolidityIcons

private val CAPTION = "New Solidity File"
private val CONTRACT_TEMPLATE = "Solidity Contract"
private val LIBRARY_TEMPLATE = "Solidity Library"

class SolidityCreateFileAction : CreateFileFromTemplateAction(CAPTION, "", SolidityIcons.FILE_ICON), DumbAware {

  override fun getActionName(directory: PsiDirectory?, newName: String?, templateName: String?) = CAPTION

  override fun buildDialog(project: Project?, directory: PsiDirectory?,
                           builder: CreateFileFromTemplateDialog.Builder) {
    builder.setTitle(CAPTION)
      .addKind("Smart contract", SolidityIcons.FILE_ICON, CONTRACT_TEMPLATE)
      .addKind("Solidity library", SolidityIcons.FILE_ICON, LIBRARY_TEMPLATE)
      .setValidator(object : InputValidatorEx {
        override fun checkInput(inputString: String): Boolean {
          return getErrorText(inputString) == null
        }

        override fun canClose(inputString: String): Boolean {
          return getErrorText(inputString) == null
        }

        override fun getErrorText(inputString: String): String? {
          return if (!StringUtil.isEmpty(inputString) && FileUtil.sanitizeFileName(inputString, false) == inputString)
            null
          else
            "'$inputString' is not a valid Solidity module name"
        }
      })
  }
}