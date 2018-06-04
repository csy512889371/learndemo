package com.dt.scala.hello

object TupleOps {

  def main(args: Array[String]): Unit = {

    val triple = (100, "Scala", "Spark")
    println(triple._1)
    println(triple._2)
    println(triple._3)

  }

}