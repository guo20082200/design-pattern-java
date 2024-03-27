package com.zishi.scala.a02.okk05

object Test08_Practice01 {
  def main(args: Array[String]): Unit = {
    // 1. 练习1
    val fun = (i: Int, s: String, c: Char) => {
      if (i == 0 && s == "" && c == '0') false else true
    }

    println(fun(0, "", '0'))//false
    println(fun(0, "", '1'))//true
    println(fun(23, "", '0'))//true
    println(fun(0, "hello", '0'))//true
  }
}