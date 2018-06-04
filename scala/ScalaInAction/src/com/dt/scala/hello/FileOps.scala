package com.dt.scala.hello

import scala.io.Source

object FileOps {
  def main(args: Array[String]) {
    //		val file = Source.fromFile("E:\\ctoedu.txt")
    val file = Source.fromURL("http://spark.apache.org/")
    for (line <- file.getLines) {
      println(line)
    }
  }
}