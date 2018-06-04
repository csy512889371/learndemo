package com.dt.scala.gui

import scala.swing._
import java.io.File
import scala.swing.event.ButtonClicked
import scala.swing.Label

object GUI_Event extends SimpleSwingApplication {

  val fileChooser = new FileChooser(new File("."))
  fileChooser.title = "File Chooser"
  val button = new Button {
    text = "Choose a File from local"
  }
  val label = new Label {
    text = "No any file selected yet."
  }
  val mainPanel = new FlowPanel {
    contents += button
    contents += label
  }

  def top = new MainFrame {
    title = "Scala GUI Programing advanced!!!"
    contents = mainPanel

    listenTo(button)

    reactions += {
      case ButtonClicked(b) => {
        val result = fileChooser.showOpenDialog(mainPanel)
        if (result == FileChooser.Result.Approve) {
          label.text = fileChooser.selectedFile.getPath()
        }
      }
    }
  }

}