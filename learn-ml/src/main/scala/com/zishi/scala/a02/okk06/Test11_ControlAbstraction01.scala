package com.zishi.scala.a02.okk06

object Test11_ControlAbstraction01 {
  def main(args: Array[String]): Unit = {
    // 1. 传值参数
    def f0(a: Int): Unit = {
      println("a: " + a)
      println("a: " + a)
    }

    f0(23)

    // 无参数，返回Int
    def f1(): Int = {
      println("f1调用")
      12
    }

    // f0里面调用f1
    f0(f1())
  }
}