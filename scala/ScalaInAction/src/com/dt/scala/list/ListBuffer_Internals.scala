package com.dt.scala.list


object ListBuffer_Internals {

  def main(args: Array[String]) {
    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    increment(list)
    increment_MoreEffective(list)
    increment_MostEffective(list)
  }

  def increment(list: List[Int]): List[Int] = list match {
    case List() => List()
    case head :: tail => head + 1 :: increment(tail)
  }

  def increment_MoreEffective(list: List[Int]): List[Int] = {
    var result = List[Int]()
    for (element <- list) result = result ::: List(element + 1)
    result
  }

  def increment_MostEffective(list: List[Int]): List[Int] = {
    import scala.collection.mutable.ListBuffer
    var buffer = new ListBuffer[Int]
    for (element <- list) buffer += element + 1
    buffer.toList
  }

}