package com.zishi.scala.a01.okk02

object Test07_DataType02 {
  def main(args: Array[String]): Unit = {
    //3、字符类型
    val c1: Char = 'a'
    println(c1)

    val c2: Char = '9'
    println(c2)

    //控制字符
    val c3: Char = '\t' //制表符
    val c4: Char = '\n' //换行符
    println("abc" + c3 + "def")
    println("abc" + c4 + "def")

    //转义字符
    val c5 = '\\' //表示\自身
    val c6 = '\"' //表示"
    println("abc" + c5 + "def")
    println("abc" + c6 + "def")

    //字符变量底层保存ASCII码
    val i1: Int = c1
    println("i1: " + i1)
    val i2: Int = c2
    println("i2: " + i2)

    val c7: Char = (i1 + 1).toChar
    println(c7)
    val c8: Char = (i2 - 1).toChar
    println(c8)

    //4、布尔类型
    val isTrue: Boolean = true
    println(isTrue)
  }
}