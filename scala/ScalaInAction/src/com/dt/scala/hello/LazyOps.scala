package com.dt.scala.hello

import scala.io.Source

object LazyOps {

  def main(args: Array[String]): Unit = {
    lazy val file = Source.fromFile("E:\\Sparkctoedu.txt")

    println("Scala")
    for (line <- file.getLines) println(line)
  }

}