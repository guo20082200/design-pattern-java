package com.zishi.scala.a01.okk03

object Test01_TestOperator01 {
  def main(args: Array[String]): Unit = {
    //1、算术运算符
    println("===================1、算术运算符")
    val result1: Int = 10 / 3
    println(result1) //3

    val result2: Double = 10 / 3
    println(result2) //3.0

    val result3: Double = 10.0 / 3
    println(result3.formatted("%5.2f")) //3.33

    val result4: Int = 10 % 3
    println(result4) //1
  }
}