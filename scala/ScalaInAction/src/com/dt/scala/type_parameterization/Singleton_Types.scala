package com.dt.scala.type_parameterization

class Animal {
  def breathe: this.type = this
}

class Cat extends Animal {
  def eat: this.type = this
}

object Singleton_Types {

  def main(args: Array[String]): Unit = {
    val cat = new Cat
    cat.breathe.eat

  }

}