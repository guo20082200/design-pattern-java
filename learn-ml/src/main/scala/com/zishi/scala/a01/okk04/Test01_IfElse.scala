package com.zishi.scala.a01.okk04

import scala.io.StdIn

object Test01_IfElse {
  def main(args: Array[String]): Unit = {
    println("请输入您的年龄：")
    val age: Int = StdIn.readInt()

    // 1. 单分支
    if (age >= 18) {
      println("成年")
    }

    println("===================")

    // 2. 双分支
    if (age >= 18) {
      println("成年")
    } else {
      println("未成年")
    }

    println("===================")

    // 3. 多分支
    if (age <= 6) {
      println("童年")
    } else if (age < 18) {
      println("青少年")
    } else if (age < 35) {
      println("青年")
    } else if (age < 60) {
      println("中年")
    } else {
      println("老年")
    }
    println("===================")
  }
}