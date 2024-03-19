package com.zishi.scala.okk04


import scala.collection.immutable
import scala.math.BigDecimal.double2bigDecimal

object Test02_ForLoop {
  def main(args: Array[String]): Unit = {
    // java for语法： for(int i = 0; i < 10; i++){ System.out.println(i + ". hello world") }

    // 1. 范围遍历
    for (i <- 1 to 10) {
      println(i + ". hello world")
    }
    for (i: Int <- 1.to(10)) {
      println(i + ". hello world")
    }

    for (i <- Range(1, 10)) {
      println(i + ". hello world")
    }

    for (i <- 1 until 10) {
      println(i + ". hello world")
    }

    // 2. 集合遍历
    for (i <- Array(12, 34, 53)) {
      println(i)
    }
    for (i <- List(12, 34, 53)) {
      println(i)
    }
    for (i <- Set(12, 34, 53)) {
      println(i)
    }

    // 4. 循环步长
    for (i <- 1 to 10 by 2) {
      println(i)
    }
    println("-------------------")
    for (i <- 13 to 30 by 3) {
      println(i)
    }

    println("-------------------")
    for (i <- 30 to 13 by -2) {
      println(i)
    }
    for (i <- 10 to 1 by -1) {
      println(i)
    }


    println("-------------------")
    // for (i <- 1 to 10 reverse) { 编译报错
    for (i <- 1.to(10).reverse) {
      println(i)
    }
    println("-------------------")

    //for (i <- 30 to 13 by 0) {
    //  println(i)
    //} // error，step不能为0

    // double  和 Int不一样，这里的to不是double的函数
    for (data <- 1.0 to 10.0 by 0.3) { //0.3精度会出现问题
      println(data)
    }
  }
}