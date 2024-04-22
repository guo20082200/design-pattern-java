package com.zishi.scala.a02.okk05

object Test06_HighOrderFunction01 {
  def main(args: Array[String]): Unit = {
    def f(n: Int): Int = {
      println("f调用")
      n + 1
    }



    val result: Int = f(123)
    println(result) // 124

    // 1. 函数作为值进行传递
    val f1: Int => Int = f
    val f2 = f _

    println(f1) // com.zishi.scala.okk05.Test06_HighOrderFunction$$$Lambda$16/0x00000008000d7040@1e67a849
    println(f1(12)) // 13
    println(f2) // com.zishi.scala.okk05.Test06_HighOrderFunction$$$Lambda$17/0x00000008000d6840@57d5872c
    println(f2(35)) // 36


    def fun(): Int = {
      println("fun调用")
      1
    }
    val f3: () => Int = fun
    val f4 = fun _
    println(f3) // com.zishi.scala.okk05.Test06_HighOrderFunction$$$Lambda$18/0x00000008000d6040@64c87930
    println(f4) // com.zishi.scala.okk05.Test06_HighOrderFunction$$$Lambda$19/0x00000008000d6440@400cff1a
  }
}