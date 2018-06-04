package com.dt.scala.bestpractice

object For_Advanced {

  def main(args: Array[String]): Unit = {
    for (i <- List(1, 2, 3, 4, 5)) {
      println(i)
    }

    for (index@"Flink" <- List("Hadoop", "Spark", "Flink")) {
      println(index)
    }

    for ((language, "Hadoop") <- Set("Scala" -> "Spark", "Java" -> "Hadoop")) println(language)
    for ((k, v: Int) <- List(("Spark" -> 5), ("Hadoop" -> "Big Data"))) {
      println(k)
    }
  }

}