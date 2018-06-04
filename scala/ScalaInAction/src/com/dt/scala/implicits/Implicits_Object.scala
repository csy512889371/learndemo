package com.dt.scala.implicits


abstract class Template[T] {
  def add(x: T, y: T): T
}

abstract class SubTemplate[T] extends Template[T] {
  def unit: T
}

object Implicits_Object {

  def main(args: Array[String]) {
    implicit object StringAdd extends SubTemplate[String] {
      override def add(x: String, y: String) = x concat y

      override def unit: String = ""
    }
    implicit object IntAdd extends SubTemplate[Int] {
      override def add(x: Int, y: Int) = x + y

      override def unit: Int = 0
    }
    def sum[T](xs: List[T])(implicit m: SubTemplate[T]): T =
      if (xs.isEmpty) m.unit
      else m.add(xs.head, sum(xs.tail))

    println(sum(List(1, 2, 3, 4, 5)))
    println(sum(List("Scala", "Spark", "Kafka")))

  }

}