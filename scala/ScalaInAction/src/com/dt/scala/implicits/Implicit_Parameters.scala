package com.dt.scala.implicits

object Context_Implicits {
  implicit val default: String = "Flink"
}

object Param {
  def print(content: String)(implicit language: String) {
    println(language + ":" + content)
  }
}

object Implicit_Parameters {

  def main(args: Array[String]) {
    Param.print("Spark")("Scala")

    import Context_Implicits._
    Param.print("Hadoop")
  }
}