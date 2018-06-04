package com.dt.scala.type_parameterization

import scala.reflect.ClassTag


class A[T]

object Manifest_ClassTag {

  def main(args: Array[String]) {

    def arrayMake[T: Manifest](first: T, second: T) = {
      val r = new Array[T](2);
      r(0) = first;
      r(1) = second;
      r
    }

    arrayMake(1, 2).foreach(println)

    def mkArray[T: ClassTag](elems: T*) = Array[T](elems: _*)

    mkArray(42, 13).foreach(println)
    mkArray("Japan", "Brazil", "Germany").foreach(println)

    def manif[T](x: List[T])(implicit m: Manifest[T]) = {
      if (m <:< manifest[String])
        println("List strings")
      else
        println("Some other type")
    }

    manif(List("Spark", "Hadoop"))
    manif(List(1, 2))
    manif(List("Scala", 3))

    val m = manifest[A[String]]
    println(m)
    val cm = classManifest[A[String]]
    println(cm)
  }

}