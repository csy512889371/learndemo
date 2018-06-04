package com.dt.scala.basics

import scala.io.Source
import java.io.PrintWriter
import java.io.File

object FileOps {
	def main(args: Array[String]) {
    val file = Source.fromFile("E:\\ctoedu.txt")
    for (line <- file.getLines) {
      println(line)
    }
    file.close
		
//		val webFile = Source.fromURL("http://spark.apache.org/")
//		webFile.foreach(print)
//		webFile.close
//		
//		val writer = new PrintWriter(new File("scalaFile.txt" ))
//        for (i <- 1 to 100) writer.println(i)
//        writer.close()
//        
        print("Please enter your input : " )
        val line = readLine
        println("Thanks, you just typed: " + line)
	}
}