package com.zishi.scala.okk05

/**
 * 函数作为返回值返回
 * 类比Java
 * public User test() {
 * User user = new User();
 * return user;
 * }
 *
 * public User test() {
 * return new User();
 * }
 */
object Test05_Lambda03 {
  def main(args: Array[String]): Unit = {
    // 函数作为返回值返回

    def outer() = {
      def inner(): Unit = {
        print("abc")
      }

      inner _
    }

    val f1 = outer()
    f1()

    //下面的调用更诡异
    outer()() // abc

    println("...............")
    def outer2() = {
      //def inner(name: String): Unit = print(s"abc${name}")
      def inner(name: String): Unit = print(s"abc${name}")
      inner _
    }

    val f2 = outer2()
    f2("aege") // abcabcaege


    def outer3(x: Int) = {
      def mid(f : (Int, Int) => Int) = {
        def inner(y: Int) = {
          f(x, y)
        }
        inner _
      }
      mid _
    }

    println()

    val mid = outer3(1)
    val inner = mid( _ + _)
    val res = inner(3)
    println(res)

    val res2 = outer3(1)( _ + _)(3)
    println(res2)
  }
}