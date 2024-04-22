package com.zishi.scala.a05.okk13

object Test01_Exception {
  def main(args: Array[String]): Unit = {
    try {
      val n = 10 / 0
    } catch {
      case e: ArithmeticException => println("发生算术异常！" + e.getMessage)
      case e: Exception => println("发生一般异常！" + e.getMessage)
    } finally {
      println("处理结束！")
    }
  }
}