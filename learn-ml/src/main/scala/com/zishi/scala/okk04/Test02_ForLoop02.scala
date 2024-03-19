package com.zishi.scala.okk04

import scala.math.BigDecimal.double2bigDecimal

object Test02_ForLoop02 {
  def main(args: Array[String]): Unit = {

    // for (i <- 1 to 10 reverse) { 编译报错
    for (i <- 1.to(10).reverse) {
      println(i)
    }
    println("-------------------")

    // double  和 Int不一样，这里的to不是double的函数
    for (data <- 1.0 to 10.0 by 0.3) { //0.3精度会出现问题
      println(data)
    }
  }
}