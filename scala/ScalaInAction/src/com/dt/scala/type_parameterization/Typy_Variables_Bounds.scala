package com.dt.scala.type_parameterization


//class Pair[T](val first : T, val second : T)
class Pair[T <: Comparable[T]](val first: T, val second: T) {
  def bigger = if (first.compareTo(second) > 0) first else second
}

class Pair_Lower_Bound[T](val first: T, val second: T) {
  def replaceFirst[R >: T](newFirst: R) = new Pair_Lower_Bound[R](newFirst, second)
}

object Typy_Variables_Bounds {

  def main(args: Array[String]) {
    val pair = new Pair("Spark", "Hadoop")
    println(pair.bigger)
  }
}