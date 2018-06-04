package com.dt.scala.type_parameterization


class Outer {
  private val x = 10

  class Inner {
    private val y = x + 10
  }

}

object Path_Dependence {

  def main(args: Array[String]) {
    val outer = new Outer
    val inner = new outer.Inner
    val inner2: outer.Inner = new outer.Inner

    val o1 = new Outer
    val o2 = new Outer
    val i: Outer#Inner = new o1.Inner


  }

}