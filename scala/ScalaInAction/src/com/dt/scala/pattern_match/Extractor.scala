package com.dt.scala.pattern_match

object Extractor {

  def main(args: Array[String]) {

    def match_array(arr: Any) = arr match {
      case Array(0) => println("Array:" + "0")
      case Array(x, y) => println("Array:" + x + " " + y)
      case Array(0, _*) => println("Array:" + "0 ...")
      case _ => println("something else")
    }

    match_array(Array(0))
    match_array(Array(0, 1))
    match_array(Array(0, 1, 2, 3, 4, 5))

    val pattern = "([0-9]+) ([a-z]+)".r
    "20150628 hadoop" match {
      case pattern(num, item) => println(num + " : " + item)
    }


  }

}