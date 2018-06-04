package com.dt.scala.actor

import scala.actors.Actor


case class Message(content: String, sender: Actor)

class LeoTelephoneActor extends Actor {
  def act() {
    while (true) {
      receive {
        case Message(content, sender) => {
          println("leo telephone: " + content);
          sender ! "I'm leo, please call me after 10 minutes."
        }
      }
    }
  }
}

class JackTelephoneActor(val leoTelephoneActor: Actor) extends Actor {
  def act() {
    leoTelephoneActor ! Message("Hello, Leo, I'm Jack.", this)
    receive {
      case response: String => println("jack telephone: " + response)
    }
  }
}


object JackTelephoneActor {
  def main(args: Array[String]): Unit = {
    val leoActor = new LeoTelephoneActor()
    val jackActor = new JackTelephoneActor(leoActor)

    leoActor.start
    jackActor.start
  }

}
