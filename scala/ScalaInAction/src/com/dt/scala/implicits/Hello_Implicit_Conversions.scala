package com.dt.scala.implicits

import scala.io.Source
import java.io.File

class RichFile(val file: File) {
  def read = Source.fromFile(file.getPath()).mkString
}

object Context {
  implicit def file2RichFile(file: File) = new RichFile(file) //File -> RichFile

}

object Hello_Implicit_Conversions {

  def main(args: Array[String]) {
    import Context.file2RichFile
    println(new File("E:\\ctoedu.txt").read)
  }

}