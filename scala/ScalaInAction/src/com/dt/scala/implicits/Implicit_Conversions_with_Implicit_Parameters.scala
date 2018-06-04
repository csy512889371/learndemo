package com.dt.scala.implicits

object Implicit_Conversions_with_Implicit_Parameters {

  def main(args: Array[String]) {
    def bigger[T](a: T, b: T)(implicit ordered: T => Ordered[T])
    = if (a > b) a else b

    println(bigger(4, 3))
    println(bigger(4.2, 3))
    println(bigger("Spark", "Hadoop"))
  }

}