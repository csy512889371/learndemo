package com.dt.scala.oop

import scala.reflect.ClassTag


class Person {
  private var age = 0

  def increment() {
    age += 1
  }

  def current = age
}

class Student extends Person {
  private var privateAge = 0
  val name = "Scala"

  def age = privateAge

  def isYounger(other: Student) = privateAge < other.privateAge
}

class Pair_Lower_Bound[T](val first: T, val second: T) {
  def replaceFirst[R >: T](newFirst: R) = new Pair_Lower_Bound[R](newFirst, second)
}

object HelloOOP {

  def main(args: Array[String]): Unit = {


    val person = new Person()

    person.increment()
    person.increment()
    println(person.current)
    //
    //    val student = new Student
    //    student.age = 10
    //    println(student.age)

    val student = new Student

    val studentTwo = new Student
    println(student.name)

    val pair = new Pair_Lower_Bound(student, studentTwo)
    pair.replaceFirst(person)

    val pair1 = new Pair_Lower_Bound(person, person)
    pair1.replaceFirst(student)
  }

}