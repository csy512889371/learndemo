package com.dt.scala.type_parameterization


trait Logger {
  def log(msg: String)
}

trait Auth {
  auth: Logger =>
  def act(msg: String) {
    log(msg)
  }
}

object DI extends Auth with Logger {
  override def log(msg: String) = println(msg);
}

object Dependency_Injection {

  def main(args: Array[String]) {
    DI.act("I hope you'll like it")
  }

}