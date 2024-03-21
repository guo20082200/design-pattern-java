package com.zishi.scala.okk05

object Test06_HighOrderFunction02 {
  def main(args: Array[String]): Unit = {
    // 2. 函数作为参数进行传递
    // 定义二元计算函数
    def dualEval(op: (Int, Int) => Int, a: Int, b: Int): Int = {
      op(a, b)
    }

    def add(a: Int, b: Int): Int = {
      a + b
    }

    println(dualEval(add, 12, 35))
    println(dualEval((a, b) => a + b, 12, 35))
    println(dualEval(_ + _, 12, 35))

    // 3. 函数作为函数的返回值返回
    def f5(): Int => Unit = {
      def f6(a: Int): Unit = {
        println("f6调用 " + a)
      }
      f6 // 将函数直接返回
    }

    //    val f6 = f5()
    //    println(f6)
    //    println(f6(25))

    println(f5()(25))
  }
}