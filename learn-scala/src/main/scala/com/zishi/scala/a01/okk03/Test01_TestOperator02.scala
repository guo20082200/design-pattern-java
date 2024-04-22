package com.zishi.scala.a01.okk03

object Test01_TestOperator02 {
  def main(args: Array[String]): Unit = {
    //2、比较运算符
    println("===================2、比较运算符")
    val s1: String = "hello"
    val s2: String = new String("hello")

    println(s1 == s2) //true
    println(s1.equals(s2)) //true
    println(s1.eq(s2)) //false
  }
}