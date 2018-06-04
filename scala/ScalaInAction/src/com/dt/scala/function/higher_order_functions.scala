package com.dt.scala.function

import scala.math._

object higher_order_functions {

  def main(args: Array[String]) {

    (1 to 9).map("*" * _).foreach(println _)
    (1 to 9).filter(_ % 2 == 0).foreach(println)
    println((1 to 9).reduceLeft(_ * _))
    "Spark is the most exciting thing happening in big data today".split(" ").
      sortWith(_.length < _.length).foreach(println)


    val fun = ceil _
    val num = 3.14
    println(fun(num))
    Array(3.14, 1.42, 2.0).map(fun).foreach(println)

    val triple = (x: Double) => 3 * x
    Array(3.14, 1.42, 2.0).map((x: Double) => 3 * x)
    Array(3.14, 1.42, 2.0).map { (x: Double) => 3 * x }

    def high_order_functions(f: (Double) => Double) = f(0.25)

    println(high_order_functions(ceil _))
    println(high_order_functions(sqrt _))

    def mulBy(factor: Double) = (x: Double) => factor * x

    val quintuple = mulBy(5)
    println(quintuple(20))

    println(high_order_functions((x: Double) => 3 * x))
    high_order_functions((x) => 3 * x)
    high_order_functions(x => 3 * x)

    println(high_order_functions(3 * _))

    val fun2 = 3 * (_: Double)
    val fun3: (Double) => Double = 3 * _


  }

}