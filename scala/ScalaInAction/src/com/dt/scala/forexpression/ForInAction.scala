package com.dt.scala.forexpression

object ForInAction {

  def main(args: Array[String]) {
    val lauren = Person("Lauren", false)
    val rocky = Person("Rocky", true)
    val vivian = Person("Vivian", false, lauren, rocky)
    val persons = List(lauren, rocky, vivian)
    //生成器 定义 过滤器 处理
    val forResult = for {person <- persons; name = person.name; if !person.isMale; child <- person.children}
      yield (person.name, child.name)
    println(forResult)

    val content = for (x <- List(1, 2, 3); y <- List("Hadoop", "Spark", "Flink")) yield (x, y)
    println(content)
  }

}