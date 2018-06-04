package com.dt.scala.implicits

object Evidence {

  def main(args: Array[String]) {
    def evidence[T](i: T)(implicit ev: T <:< java.io.Serializable) {
      print("Spark!!!")
    }

    evidence("Spark")
    //    test(140)
  }

}