package com.dt.scala.oop

class UseTrait {

}

trait Logger {
  //  def log (msg : String)
  def log(msg: String): Unit = {
    println("logger")
  }
}

class ConcreteLogger extends Logger with Cloneable {

  override def log(msg: String) = println("Log: " + msg)

  def concreteLog {
    log("It's me !!!")
  }
}

trait TraitLogger extends Logger {
  override def log(msg: String) {
    println(" TraitLogger Log content is : " + msg)
  }
}

trait TraitLoggered {
  def loged(msg: String) {
    println("TraitLoggered Log content is : " + msg)
  }
}

trait ConsoleLogger extends TraitLogger {
  override def log(msg: String) {
    println("Log from Console :" + msg)
  }
}

class Test extends ConsoleLogger {
  def test {
    log("Here is Spark!!!")
  }

}

abstract class Account {
  def save
}

class MyAccount extends Account with ConsoleLogger {
  def save {
    log("11")
  }
}

class Human {
  println("Human")

  def go = {
    println("go go go")
  }
}

trait TTeacher extends Human {
  println("TTeacher")

  def teach
}

trait PianoPlayer extends Human {
  println("PianoPlayer")

  def playPiano = {
    println("Im playing piano. ")
  }
}

class PianoTeacher extends Human with TTeacher with PianoPlayer {
  override def teach = {
    println("Im training students. ")
  }
}

//AOP
trait Action {
  def doAction
}

trait TBeforeAfter extends Action {
  abstract override def doAction {
    println("Initialization")
    super.doAction
    println("Destroyed")
  }
}

class Work extends Action {
  override def doAction = println("Working...")
}

object UseTrait extends App {
  val t1 = new PianoTeacher
  t1.playPiano
  t1.go
  t1.teach

  val t2 = new Human with TTeacher with PianoPlayer {
    def teach = {
      println("I'm teaching students.")
    }
  }
  t2.playPiano
  t2 teach


  val work = new Work with TBeforeAfter
  work.doAction

  val logger = new ConcreteLogger with TraitLogger
  //  val logger = new ConcreteLogger
  //logger.concreteLog
  //val logger = new Test
  //logger.test;

  val account = new MyAccount with TraitLoggered
  account.save

}