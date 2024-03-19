package com.zishi.scala.okk03

object Test01_TestOperator03 {
  def main(args: Array[String]): Unit = {
    //3、逻辑运算符
    println("===================3、逻辑运算符")

    def m(n: Int): Int = {
      println("m被调用")
      return n
    }

    val n = 1
    println((4 > 5) && m(n) > 0) //false

    //判断一个字符串是否为空
    def isNotEmpty(str: String): Boolean = {
      return str != null && !("".equals(str.trim))
    }

    println(isNotEmpty(null)) //false
  }
}