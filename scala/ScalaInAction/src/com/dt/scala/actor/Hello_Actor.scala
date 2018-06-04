package com.dt.scala.actor

import scala.actors.Actor

object First_Actor extends Actor {
  def act() {
    println(Thread.currentThread().getName())
    for (i <- 1 to 10) {
      println("Step : " + i)

      Thread.sleep(2000)
    }

  }
}

object Second_Actor extends Actor {
  def act() {
    println(Thread.currentThread().getName())
    for (i <- 1 to 10) {
      println("Step Further : " + i)

      Thread.sleep(2000)
    }

  }
}

object Hello_Actor {

  def main(args: Array[String]) {
    First_Actor.start
    Second_Actor.start
  }

}