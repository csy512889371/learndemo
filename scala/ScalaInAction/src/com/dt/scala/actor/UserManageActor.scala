package com.dt.scala.actor

import scala.actors.Actor

case class Login(username: String, password: String)

case class Register(username: String, password: String)

class UserManageActor extends Actor {
  def act() {
    while (true) {
      receive {
        case Login(username, password) => println("login, username is " + username + ", password is " + password)
        case Register(username, password) => println("register, username is " + username + ", password is " + password)
      }
    }
  }
}

object UserManageActor {
  def main(args: Array[String]): Unit = {
    val userManageActor = new UserManageActor
    userManageActor.start()
    userManageActor ! Register("nick", "1234");
    userManageActor ! Login("nick", "1234")
  }
}