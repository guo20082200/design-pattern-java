package com.zishi.scala.a02.okk05

/**
 * 函数至简原则
 */
object Test04_Simplify {

  def main(args: Array[String]): Unit = {

    def f0(name: String): String = {
      return name
    }

    println(f0("erfearfere"))

    println("==========================")

    //（1）return可以省略，Scala会使用函数体的最后一行代码作为返回值
    def f1(name: String): String = {
      name
    }

    println(f1("erfearfere"))

    println("==========================")

    //（2）如果函数体只有一行代码，可以省略花括号
    def f2(name: String): String = name

    println(f2("erfearfere"))

    println("==========================")

    //    （3）返回值类型如果能够推断出来，那么可以省略（:和返回值类型一起省略）
    def f3(name: String) = name

    println(f3("erfearfere"))

    println("==========================")

    //    （4）如果有return，则不能省略返回值类型，必须指定
    //    def f4(name: String) = {
    //      return name
    //    }
    //
    //    println(f4("erfearfere"))

    println("==========================")

    //    （5）如果函数明确声明unit，那么即使函数体中使用return关键字也不起作用
    def f5(name: String): Unit = {
      return name
    }

    println(f5("f5 erfearfere"))

    println("==========================")

    /**
     * （6）Scala如果期望是无返回值类型，可以省略等号
     * =号省略的前提是 没有返回值
     * @param name
     */
    def f6(name: String) {
      println(name)
    }

    println(f6("f6 erfearfere"))

    println("==========================")

    //    （7）如果函数无参，但是声明了参数列表，那么调用时，小括号，可加可不加
    def f7(): Unit = {
      println("f7")
    }

    f7()
    f7

    println("==========================")

    //    （8）如果函数没有参数列表，那么小括号可以省略，调用时小括号必须省略
    def f8: Unit = {
      println("f8  f8")
    }

    /**
     * 省略参数列表的小括号
     * 省略返回值类型
     * 省略方法体的花括号
     */
    def f82 = println("f8  f8")


    //    f8()
    f8

    println("==========================")

    //    （9）如果不关心名称，只关心逻辑处理，那么函数名（def）可以省略
    def f9(name: String): Unit = {
      println(name)
    }

    // 匿名函数，lambda表达式，无参函数
    () => {
      println("Hello lambda")
    }

    // 匿名函数，lambda 表达式，这里是有参函数
    (name: String) => {
      println(s"aaa ${name}", name)
    }

    println("-------------------")
  }
}