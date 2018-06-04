package com.dt.scala.implicits

import scala.io.Source
import java.io.File

class RicherFile(val file:File){
   def read = Source.fromFile(file.getPath()).mkString
}
 
class File_Implicits( path: String) extends File(path)
object File_Implicits{
    implicit def file2RicherFile(file:File)= new RicherFile(file) //File -> RicherFile
}

object Implicits_Internals {
	def main(args: Array[String]) {
		println(new File_Implicits("E:\\ctoedu.txt").read)
	}
}