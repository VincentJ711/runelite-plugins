package com.itemcarts.ui.mainview

import com.itemcarts.IViewsCtrl
import com.itemcarts.Model
import com.itemcarts.ui.Destroyable
import com.itemcarts.ui.InteractiveLabel
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JFileChooser
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.filechooser.FileSystemView

class MainView(ctrl: IViewsCtrl) : JPanel(), Destroyable {
  private val cartsPanel = CartsPanel(ctrl)

  private val sortBtn = InteractiveLabel("\u21d5", "Asdfsbbbbf") {
    println("sort requested")
  }

  private val exportBtn = InteractiveLabel("\u21f1", "Asdfsfsss") {
    println("export requested")
  }

  private val importBtn = InteractiveLabel("\u21f2", "Asdfsfssssss") {
    println("import requested")
    val j = JFileChooser(FileSystemView.getFileSystemView().homeDirectory);
    val filter = FileNameExtensionFilter("json files", "json")
    j.fileFilter = filter
    j.showOpenDialog(null)
    val file = j.selectedFile ?: return@InteractiveLabel
    val json = file.readText()
    ctrl.importCarts(json)
  }

  private val createBtn = InteractiveLabel("\uff0b", "Asdfsf") {
    println("create requested")
  }

  init {
    layout = BoxLayout(this, BoxLayout.Y_AXIS)
    importBtn.setFontSize(22)
    exportBtn.setFontSize(22)
    sortBtn.setFontSize(28)
    createBtn.setFontSize(24)
    importBtn.border = BorderFactory.createEmptyBorder(4, 4, 0, 0)
    exportBtn.border = BorderFactory.createEmptyBorder(4, 0, 0, 0)
    createBtn.border = BorderFactory.createEmptyBorder(5, 0, 0, 0)
    importBtn.toolTipText = "import"
    exportBtn.toolTipText = "export"
    sortBtn.toolTipText = "sort"
    createBtn.toolTipText = "add cart"

    val ctrlRow = JPanel(BorderLayout())
    val westCtrls = JPanel(BorderLayout(10, 0))
    val eastCtrls = JPanel(BorderLayout(10, 0))
    westCtrls.add(importBtn, BorderLayout.WEST)
    westCtrls.add(exportBtn, BorderLayout.EAST)
    eastCtrls.add(sortBtn, BorderLayout.WEST)
    eastCtrls.add(createBtn, BorderLayout.EAST)
    ctrlRow.add(westCtrls, BorderLayout.WEST)
    ctrlRow.add(JPanel(), BorderLayout.CENTER)
    ctrlRow.add(eastCtrls, BorderLayout.EAST)

    add(ctrlRow)
    add(JLabel("item carts", SwingConstants.CENTER))
    add(cartsPanel)
  }

  override fun onBeforeDestroy() {
    cartsPanel.onBeforeDestroy()
    sortBtn.onBeforeDestroy()
    exportBtn.onBeforeDestroy()
    createBtn.onBeforeDestroy()
  }

  fun refresh(model: Model) {
    cartsPanel.refresh(model)
  }
}