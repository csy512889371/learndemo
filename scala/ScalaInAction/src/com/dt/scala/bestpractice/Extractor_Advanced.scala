package com.dt.scala.bestpractice


object :> {
  def unapply[A](list: List[A]) = {
    Some((list.init, list.last))
  }
}

object Extractor_Advanced {

  def main(args: Array[String]) {
    (1 to 9).toList match {
      case _ :> 9 => println("Hadoop")
    }
    (1 to 9).toList match {
      case x :> 8 :> 9 => println("Spark")
    }
    (1 to 9).toList match {
      case :>(:>(_, 8), 9) => println("Flink")
    }

  }

}