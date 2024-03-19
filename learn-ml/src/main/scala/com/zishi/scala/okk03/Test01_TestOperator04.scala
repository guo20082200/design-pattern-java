package com.zishi.scala.okk03

object Test01_TestOperator04 {
  def main(args: Array[String]): Unit = {
    //4、赋值运算符
    println("===================4、赋值运算符")
    //var b: Byte = 10
    var i: Int = 12
    //b += 1
    i += 1
    println(i) //13

    //i ++

    //6、运算符的本质
    println("===================6、运算符的本质")
    val n1: Int = 12
    val n2: Int = 37

    println(n1.+(n2)) //49
    println(n1 + n2) //49

    println(1.34.*(25)) //33.5
    println(1.34 * 25) //33.5

  }
}