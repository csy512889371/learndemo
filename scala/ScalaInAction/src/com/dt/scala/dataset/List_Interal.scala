package com.dt.scala.dataset

object List_Interal {

  def main(args: Array[String]) {

    val list: List[Int] = List(1, 2, 3, 4, 5)
    val listAny: List[Any] = list
    println(list.isEmpty)
    println(list.head)
    println(list.tail)
    println(list.length)
    println(list.drop(2))
    list.map(_ * 2)

  }

}