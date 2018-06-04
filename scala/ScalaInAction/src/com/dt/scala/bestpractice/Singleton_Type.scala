package com.dt.scala.bestpractice

import scala.reflect.runtime.universe._

object Scala

class Java1

class JVM {
  def method1: this.type = this
}

class JVM_Language extends JVM {
  def method2: this.type = this
}

object Singleton_Type {

  def main(args: Array[String]) {
    println(Scala.getClass)
    println(typeOf[Scala.type])

    val java = new Java1
    val java2 = new Java1
    println(typeOf[java.type])
    println(typeOf[java2.type])
    val content: java.type = java
    //    val content:java.type = java2

    val jvm = new JVM_Language
    println(jvm.method1)
    jvm.method1.method2
  }

}