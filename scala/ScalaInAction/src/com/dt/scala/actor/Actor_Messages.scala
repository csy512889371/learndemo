package com.dt.scala.actor

import scala.actors.Actor._
import scala.actors.Actor

object Actor_Message extends Actor {
  def act() {
    while (true) {
      receive {
        case msg => println("Message content Actor from inbox: " + msg)
      }
    }
  }
}

object Actor_Messages {

  def main(args: Array[String]) {
    val actor_Message = actor {
      while (true) {
        receive { //apply isDefinedAt
          case msg => println("Message content from inbox: " + msg)
        }
      }
    }
    val double_Message = actor {
      while (true) {
        receive {
          case msg: Double => println("Double Number from inbox: " + msg)
          //    		case _ => println("Something Unkown" )
        }
      }
    }
    Actor_Message.start
    Actor_Message ! "Hadoop"
    actor_Message ! "Spark"
    double_Message ! Math.PI
    double_Message ! "Hadoop"

  }

}