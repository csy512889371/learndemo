package com.dt.scala.type_parameterization

trait Compound_Type1;

trait Compound_Type2;

class Compound_Type extends Compound_Type1 with Compound_Type2

object Compound_Type {
  def compound_Type(x: Compound_Type1 with Compound_Type2) = {
    println("Compound Type in global method")
  }

  def main(args: Array[String]) {

    compound_Type(new Compound_Type1 with Compound_Type2)
    object compound_Type_oject extends Compound_Type1 with Compound_Type2
    compound_Type(compound_Type_oject)

    type compound_Type_Alias = Compound_Type1 with Compound_Type2

    def compound_Type_Local(x: compound_Type_Alias) = println("Compound Type in local method")

    val compound_Type_Class = new Compound_Type
    compound_Type_Local(compound_Type_Class)

    type Scala = Compound_Type1 with Compound_Type2 {def init(): Unit}
  }

}