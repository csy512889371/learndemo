package com.dt.scala.gui

import scala.swing._

object Hello_GUI extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Hello GUI"
    contents = new Button {
      text = "Scala => Spark!!!"
    }
  }
}