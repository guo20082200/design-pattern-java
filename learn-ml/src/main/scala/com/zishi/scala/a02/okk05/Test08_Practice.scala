package com.zishi.scala.a02.okk05

/**
 * 函数作为返回值
 */
object Test08_Practice {
  def main(args: Array[String]): Unit = {
    // 2. 练习2 内层函数可以使用外层函数的参数

    // func 参数列表：i: Int， 返回值类型（函数类型）：String => (Char => Boolean)
    def func(i: Int): String => (Char => Boolean) = {
      // f1函数: 参数列表：s: String, 返回值类型：Char => Boolean
      def f1(s: String): Char => Boolean = {
        // f2函数: 参数列表：c: Char, 返回值类型：Boolean
        def f2(c: Char): Boolean = {
          if (i == 0 && s == "" && c == '0') false else true
        }
        f2
      }
      f1
    }

    println(func(0)("")('0'))//false
    println(func(0)("")('1'))//true
    println(func(23)("")('0'))//true
    println(func(0)("hello")('0'))//true


    def func33(i: Int): String => (Char => Boolean) = {
      // f1函数: 参数列表：s: String, 返回值类型：Char => Boolean
      def f1(s: String): Char => Boolean = {
        // f2函数: 参数列表：c: Char, 返回值类型：Boolean
         // def f2(c: Char): Boolean = if (i == 0 && s == "" && c == '0') false else true
         //def f2(c: Char) = if (i == 0 && s == "" && c == '0') false else true
         // val f2 = (c: Char) => if (i == 0 && s == "" && c == '0') false else true
         // c => if (i == 0 && s == "" && c == '0') false else true
        c => true
      }
      f1
    }

    def func44(i: Int): String => (Char => Boolean) = {
      // f1函数: 参数列表：s: String, 返回值类型：Char => Boolean
      //def f1(s: String): Char => Boolean = c => if (i == 0 && s == "" && c == '0') false else true
      //def f1(s: String) = (c:Char) => if (i == 0 && s == "" && c == '0') false else true
      //val f1 = (s: String) => (c:Char) => if (i == 0 && s == "" && c == '0') false else true
      //(s: String) => (c:Char) => if (i == 0 && s == "" && c == '0') false else true
      s => c => if (i == 0 && s == "" && c == '0') false else true
    }

    // 匿名函数简写
    def func1(i: Int): String => (Char => Boolean) = {
      s => c => if (i == 0 && s == "" && c == '0') false else true
    }

    println(func1(0)("")('0'))//false
    println(func1(0)("")('1'))//true
    println(func1(23)("")('0'))//true
    println(func1(0)("hello")('0'))//true

    // 柯里化
    def func2(i: Int)(s: String)(c: Char): Boolean = {
      if (i == 0 && s == "" && c == '0') false else true
    }

    println(func2(0)("")('0'))//false
    println(func2(0)("")('1'))//true
    println(func2(23)("")('0'))//true
    println(func2(0)("hello")('0'))//true
  }
}