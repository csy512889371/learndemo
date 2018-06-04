package com.dt.scala.hello

object ScalaInAction {
  println("Welcome to the Scala worksheet") //> Welcome to the Scala worksheet
  val nums = new Array[Int](10) //> nums  : Array[Int] = Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
  val a = new Array[String](10) //> a  : Array[String] = Array(null, null, null, null, null, null, null, null, n
  //| ull, null)
  val s = Array("Hello", "World") //> s  : Array[String] = Array(Hello, World)
  s(0) = "Goodbye"
  s //> res0: Array[String] = Array(Goodbye, World)
  import scala.collection.mutable.ArrayBuffer

  val b = ArrayBuffer[Int]() //> b  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer()
  b += 1 //> res1: com.dt.scala.hello.ScalaInAction.b.type = ArrayBuffer(1)
  b += (1, 2, 3, 5) //> res2: com.dt.scala.hello.ScalaInAction.b.type = ArrayBuffer(1, 1, 2, 3, 5)
  b ++= Array(8, 13, 21) //> res3: com.dt.scala.hello.ScalaInAction.b.type = ArrayBuffer(1, 1, 2, 3, 5, 8
  //| , 13, 21)
  b //> res4: scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 1, 2, 3, 5,
  //|  8, 13, 21)

  b.trimEnd(5)
  b //> res5: scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 1, 2)
  b.insert(2, 6)
  b //> res6: scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 1, 6, 2)
  b.insert(2, 7, 8, 9)
  b //> res7: scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 1, 7, 8, 9,
  //|  6, 2)
  b.remove(2) //> res8: Int = 7
  b //> res9: scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 1, 8, 9, 6,
  //|  2)
  b.remove(2, 3)
  b //> res10: scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 1, 2)
  b.toArray //> res11: Array[Int] = Array(1, 1, 2)
  b //> res12: scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 1, 2)
  for (i <- 0 until a.length)
    println(i + ": " + a(i)) //> 0: null
  //| 1: null
  //| 2: null
  //| 3: null
  //| 4: null
  //| 5: null
  //| 6: null
  //| 7: null
  //| 8: null
  //| 9: null

  val c = Array(2, 3, 5, 7, 11) //> c  : Array[Int] = Array(2, 3, 5, 7, 11)
  val result = for (elem <- c) yield 2 * elem //> result  : Array[Int] = Array(4, 6, 10, 14, 22)

  for (elem <- c if elem % 2 == 0) yield 2 * elem //> res13: Array[Int] = Array(4)
  c.filter(_ % 2 == 0).map(2 * _) //> res14: Array[Int] = Array(4)

  Array(1, 7, 2, 9).sum //> res15: Int = 19

  ArrayBuffer("Mary", "had", "a", "little", "lamb").max
  //> res16: String = little
  val d = ArrayBuffer(1, 7, 2, 9) //> d  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 7, 2, 9)
  val bSorted = d.sorted //> bSorted  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 2, 7,
  //|  9)

  val e = Array(1, 7, 2, 9) //> e  : Array[Int] = Array(1, 7, 2, 9)
  scala.util.Sorting.quickSort(e)
  e //> res17: Array[Int] = Array(1, 2, 7, 9)

  e.mkString(" and ") //> res18: String = 1 and 2 and 7 and 9
  e.mkString("<", ",", ">") //> res19: String = <1,2,7,9>


  val matrix = Array.ofDim[Double](3, 4) //> matrix  : Array[Array[Double]] = Array(Array(0.0, 0.0, 0.0, 0.0), Array(0.0
  //| , 0.0, 0.0, 0.0), Array(0.0, 0.0, 0.0, 0.0))
  matrix(2)(1) = 42
  matrix //> res20: Array[Array[Double]] = Array(Array(0.0, 0.0, 0.0, 0.0), Array(0.0, 0
  //| .0, 0.0, 0.0), Array(0.0, 42.0, 0.0, 0.0))
  val triangle = new Array[Array[Int]](10) //> triangle  : Array[Array[Int]] = Array(null, null, null, null, null, null, n
  //| ull, null, null, null)
  for (i <- 0 until triangle.length)
    triangle(i) = new Array[Int](i + 1)
  triangle //> res21: Array[Array[Int]] = Array(Array(0), Array(0, 0), Array(0, 0, 0), Arr
  //| ay(0, 0, 0, 0), Array(0, 0, 0, 0, 0), Array(0, 0, 0, 0, 0, 0), Array(0, 0,
  //| 0, 0, 0, 0, 0), Array(0, 0, 0, 0, 0, 0, 0, 0), Array(0, 0, 0, 0, 0, 0, 0, 0
  //| , 0), Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0))


  val symbols = Array("[", "-", "]") //> symbols  : Array[String] = Array([, -, ])
  val counts = Array(2, 5, 2) //> counts  : Array[Int] = Array(2, 5, 2)
  val pairs = symbols.zip(counts) //> pairs  : Array[(String, Int)] = Array(([,2), (-,5), (],2))
  for ((x, y) <- pairs) Console.print(x * y) //> [[-----]]


  val map = Map("book" -> 10, "gun" -> 18, "ipad" -> 1000) //> map  : scala.collection.immutable.Map[String,Int] = Map(book -> 10, gun ->
  //| 18, ipad -> 1000)
  for ((k, v) <- map) yield (k, v * 0.9) //> res22: scala.collection.immutable.Map[String,Double] = Map(book -> 9.0, gun
  //|  -> 16.2, ipad -> 900.0)

  val scores = scala.collection.mutable.Map("Scala" -> 7, "Hadoop" -> 8, "Spark" -> 10)
  //> scores  : scala.collection.mutable.Map[String,Int] = Map(Hadoop -> 8, Spark
  //|  -> 10, Scala -> 7)
  val hadoopScore = scores.getOrElse("Hadooop", 0) //> hadoopScore  : Int = 0


  scores += ("R" -> 9) //> res23: com.dt.scala.hello.ScalaInAction.scores.type = Map(Hadoop -> 8, R ->
  //|  9, Spark -> 10, Scala -> 7)
  scores -= "Hadoop" //> res24: com.dt.scala.hello.ScalaInAction.scores.type = Map(R -> 9, Spark ->
  //| 10, Scala -> 7)
  val sortedScore = scala.collection.immutable.SortedMap("Scala" -> 7, "Hadoop" -> 8, "Spark" -> 10)
  //> sortedScore  : scala.collection.immutable.SortedMap[String,Int] = Map(Hadoo
  //| p -> 8, Scala -> 7, Spark -> 10)

  val tuple = (1, 2, 3.14, "Rocky", "Spark") //> tuple  : (Int, Int, Double, String, String) = (1,2,3.14,Rocky,Spark)
  val third = tuple._3 //> third  : Double = 3.14


  val (first, second, thirda, fourth, fifth) = tuple //> first  : Int = 1
  //| second  : Int = 2
  //| thirda  : Double = 3.14
  //| fourth  : String = Rocky
  //| fifth  : String = Spark


  val (f1, s2, _, _, _) = tuple //> f1  : Int = 1
  //| s2  : Int = 2

  "Rocky Spark".partition(_.isUpper) //> res25: (String, String) = (RS,ocky park)


  import scala.util.matching._


  val regex ="""([0-9]+) ([a-z]+)""".r //> regex  : scala.util.matching.Regex = ([0-9]+) ([a-z]+)
  val line = "20150623 Spark" //> line  : String = 20150623 Spark
  val regex(num, blog) = line //> scala.MatchError: 20150623 Spark (of class java.lang.String)
  //| 	at com.dt.scala.hello.ScalaInAction$$anonfun$main$1.apply$mcV$sp(com.dt.
  //| scala.hello.ScalaInAction.scala:105)
  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
  //| orksheetSupport.scala:65)
  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
  //| ksheetSupport.scala:75)
  //| 	at com.dt.scala.hello.ScalaInAction$.main(com.dt.scala.hello.ScalaInActi
  //| on.scala:10)
  //| 	at com.dt.scala.hello.ScalaInAction.main(com.dt.scala.hello.ScalaInActio
  //| n.scala)


}