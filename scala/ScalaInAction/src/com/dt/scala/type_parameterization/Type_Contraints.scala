
package com.dt.scala.type_parameterization


object Type_Contraints {

  def main(args: Array[String]) {
    def rocky[T](i: T)(implicit ev: T <:< java.io.Serializable) {
      print("Life is short,you need spark!")
    }

    rocky("spark")
  }

}