package com.dt.scala.bestpractice

object Assinment_Internals {

  def main(args: Array[String]) {
    val a@b = 1000
    println("a = " + a + ", b = " + b)
    val (c, d) = (1000, 2000)
    //    val (e,F) = (1000,2000)
    val Array(g, h) = Array(1000, 2000)
    //    val Array(i,J) = Array(1000,2000)
    //    object Test { val 1 = 1 }
    object Test {
      val 1 = 2
    }

  }

}