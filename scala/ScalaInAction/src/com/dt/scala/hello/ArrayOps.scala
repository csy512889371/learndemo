package com.dt.scala.hello

import scala.collection.mutable.ArrayBuffer

object ArrayOps {

  def main(args: Array[String]): Unit = {

    // Range:to:默认步进为1
    val to1 = 1 to 10
    println(to1)

    // 定义一个不进为2的Range
    val to2 = 1 to 10 by 2
    println(to2)
    println(to2.toList)

    // Range:until
    val until1 = 1 until 10
    println(until1)
    val until2 = 1 until 10 by 2
    println(until2)


    val nums = new Array[Int](10)
    val a = new Array[String](10)
    val s = Array("Hello", "World")
    s(0) = "Goodbye"

    val b = ArrayBuffer[Int]()
    b += 1
    b += (1, 2, 3, 5)
    b ++= Array(8, 13, 21)
    b.trimEnd(5)
    b.insert(2, 6)
    b.insert(2, 7, 8, 9)
    b.remove(2)
    b.remove(2, 3)
    b.toArray

    for (i <- 0 until a.length)
      println(i + ": " + a(i))


    val c = Array(2, 3, 5, 7, 11)
    val result = for (elem <- c) yield 2 * elem
    for (elem <- c if elem % 2 == 0) yield 2 * elem
    c.filter(_ % 2 == 0).map(2 * _)

    Array(1, 7, 2, 9).sum
    ArrayBuffer("Mary", "had", "a", "little", "lamb").max

    val d = ArrayBuffer(1, 7, 2, 9)
    val bSorted = d.sorted

    val e = Array(1, 7, 2, 9)
    scala.util.Sorting.quickSort(e)

    e.mkString(" and ")
    a.mkString("<", ",", ">")

    println(e.mkString(" and "))

    val matrix = Array.ofDim[Double](3, 4)
    matrix(2)(1) = 42
    val triangle = new Array[Array[Int]](10)
    for (i <- 0 until triangle.length)
      triangle(i) = new Array[Int](i + 1)


  }

}