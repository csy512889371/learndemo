package com.dt.scala.forexpression


case class Book(title: String, authors: List[String])

object For_Query {

  def main(args: Array[String]) {
    val books: List[Book] = List(
      Book("Structure and Interpretation ", List("Abelson , Harold", "Sussman")),
      Book("Principles of Compiler Design",
        List("Aho, Alfred", "Ullman, Jeffrey")),
      Book("Programming in Modula-2", List("Wirth, Niklaus")),
      Book("Introduction to Functional Programming", List("Bird, Richard")),
      Book("The Java Language Specification",
        List("Gosling, James", "Joy, Bill", "Steele, Guy", "Bracha, Gilad")))

    //    val result = for(b <- books ; a <- b.authors if a startsWith "Gosling") yield b.title
    val result = for (b <- books if (b.title indexOf "Programming") >= 0) yield b.title
    println(result)
  }

}