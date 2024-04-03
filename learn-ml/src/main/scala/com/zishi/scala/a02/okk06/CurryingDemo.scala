package com.zishi.scala.a02.okk06

/**
 * 柯里化(Currying)指的是把原来接受多个参数的函数变换成接受一个参数的函数过程，
 * 并且返回接受余下的参数且返回结果为一个新函数的技术。
 *
 * 它有两种写法:
 */
class CurryingDemo {
  def main(args: Array[String]): Unit = {


    def f3(i: Int): Int => Int = {
      /*def f4(j: Int): Int = {
        i + j
      }*/

      // 简化1
      //def f4(j: Int): Int = i + j
      // 简化2
      //def f4(j: Int) = i + j

      // 简化3
      //val f4 = (j: Int) => i + j

      // 简化4
      (j: Int) => i + j
      // f4
    }

    def f5(i: Int): Int => Int = {
      (j: Int) => i + j
    }

    // 简化
    def f6(i: Int): Int => Int = (j: Int) => i + j





    //第一种柯里化
    def f1(x: Int): Int => Int = (y: Int) => x + y
    val f2 = f1(1)
    val result: Int = f2(2)
    println(result)

    //第二种柯里化
    def curryFunction(x: Int)(y: Int): Int = x + y

    val sum: Int = curryFunction(1)(2)
    println(sum)
  }
}
