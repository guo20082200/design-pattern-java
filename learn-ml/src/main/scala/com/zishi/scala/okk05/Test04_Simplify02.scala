package com.zishi.scala.okk05

/**
 * 函数至简原则
 */
object Test04_Simplify02 {

  def main(args: Array[String]): Unit = {
    /**
     * Scala是面向对象的语言，万物皆对象，
     * = 所以函数也是对象
     *
     * 既然函数是对象，
     * = 1. 那么函数这个对象应该有类型，2. 并且可以赋值给其他人使用
     */
    def test() = {
      println("111")
    }

    // 调用了test函数，可以省略小括号
    test
    //打印函数返回结果，Unit结果是()
    println(test)

    // 打印了test对象 com.zishi.scala.okk05.Test05_Lambda$$$Lambda$16/0x00000008000d7840@41ee392b
    println(test _)

    // 函数对象test赋值给一个变量f2
    // 函数的类型为：Function0[Unit]
    // 0表示函数参数列表为0
    // Unit表示函数的返回值为Unit
    // val f2: Function0[Unit] = test _

    // 这是 val f2: Function0[Unit] = test _的变形
    val f2: () => Unit = test _
    println(f2) //com.zishi.scala.okk05.Test04_Simplify02$$$Lambda$17/0x00000008000d7c40@57d5872c
    println("...........")
    f2() // 函数调用


    // 增加难度
    def test1(age: Int): String = {
      "hello:" + age
    }

    //函数的类型为：Function1[Int, String]
    // 1表示函数参数列表为1个
    // [Int, String]中Int表示函数的参数列表是Int,String表示返回值是String：
    // 函数对象的参数最多22个
    val f3: Function1[Int, String] = test1
    // f4 的函数类型为：(Int) => String， 输入一个Int，返回一个String
    // TODO： 这是另外一种表示形式  Function1[Int, String] 等价于 (Int) => String
    val f4: (Int) => String = test1
    println(f3) // com.zishi.scala.okk05.Test04_Simplify02$$$Lambda$18/0x00000008000d6840@36f0f1be
    println(f4) // com.zishi.scala.okk05.Test04_Simplify02$$$Lambda$19/0x00000008000d6040@6ee12bac

    f4(4) // 这里是方法调用,  类似Java中的对象调用方法： user.xxxx

  }
}