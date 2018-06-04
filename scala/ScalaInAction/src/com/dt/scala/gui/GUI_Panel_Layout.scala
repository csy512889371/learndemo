package com.dt.scala.gui

import scala.swing._
import scala.swing.event.ButtonClicked

object GUI_Panel_Layout extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Second GUI"
    val button = new Button {
      text = "Scala"
    }
    val label = new Label {
      text = "Here is Spark!!!"
    }
    contents = new BoxPanel(Orientation.Vertical) {
      contents += button
      contents += label
      border = Swing.EmptyBorder(50, 50, 50, 50)
    }

    listenTo(button)
    var clicks = 0
    reactions += {
      case ButtonClicked(button) => {
        clicks += 1
        label.text = "Clicked " + clicks + " times"

      }
    }
  }


}