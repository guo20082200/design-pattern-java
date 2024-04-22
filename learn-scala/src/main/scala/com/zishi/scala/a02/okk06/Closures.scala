package com.zishi.scala.a02.okk06

/**
 * 大致描述：闭包是一个函数，返回值依赖于声明在函数外部的一个或多个变量。
 * 闭包通常来讲可以简单的认为是可以访问一个函数里面局部变量的另外一个函数。
 *
 * 闭包：如果⼀个函数，访问到了它的外部（局部）变量的值，那么这个函数和他所处的环境，称为闭包。
 */
object Closures {

  def main(args: Array[String]): Unit = {
    val addOne = makeAdd(1)
    val addTwo = makeAdd(2)

    println(addOne(1))
    println(addTwo(1))
  }

  def makeAdd(more: Int) = (x: Int) => x + more

  def normalAdd(a: Int, b: Int) = a + b

}