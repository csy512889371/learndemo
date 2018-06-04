package com.dt.scala.actor

import scala.actors.Actor

class HelloOneActor extends Actor {
  def act() {
    while (true) {
      receive {
        case name: String => println("receive Hello, " + name)
      }
    }
  }
}

object HelloOneActor {
  def main(args: Array[String]): Unit = {
    val helloActor = new HelloOneActor
    helloActor.start()
    helloActor ! "nick"
  }
}