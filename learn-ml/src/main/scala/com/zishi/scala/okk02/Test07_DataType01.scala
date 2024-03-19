package com.zishi.scala.okk02



object Test07_DataType01 {
  def main(args: Array[String]): Unit = {
    //1、整数类型
    val a1: Byte = 127
    val a2: Byte = -128

    //val a2: Byte = 128 //error

    val a3 = 12 //整数默认类型为Int
    val a4: Long = 1324135436436L //长整型数值定义

    val b1: Byte = 10
    val b2: Byte = 10 + 20
    println(b2)

    //val b3: Byte = b1 + 20
    val b3: Byte = (b1 + 20).toByte
    println(b3)

    //2、浮点类型
    val f1: Float = 1.2345f
    val d1 = 34.2245
  }
}