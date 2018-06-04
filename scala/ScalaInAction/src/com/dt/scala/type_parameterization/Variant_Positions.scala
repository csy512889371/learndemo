package com.dt.scala.type_parameterization

//class P[+T](val first: T, val second: T)
class P[+T](val first: T, val second: T) {
  //  def replaceFirst(newFirst: T) = new P[T](newFirst, second)
  def replaceFirst[R >: T](newFirst: R) = new P[R](newFirst, second)
}

object Variant_Positions {

  def main(args: Array[String]) {

  }

}