package com.zishi.scala.a02.okk06

object Test11_ControlAbstraction {
  def main(args: Array[String]): Unit = {
    // 2. 传名参数，传递的不再是具体的值，而是代码块
    def f2(g: Int)(a: => Int): Unit = {
      println("a: " + a)
      println("a: " + a)
    }

    f2(2)(23)

    // 这里传递的是一个代码块
    f2(3)({
      println("这是一个代码块")
      29
    })

    // 简化之后， 大括号可以替代小括号
    f2(3){
      println("这是一个代码块")
      29
    }
  }
}