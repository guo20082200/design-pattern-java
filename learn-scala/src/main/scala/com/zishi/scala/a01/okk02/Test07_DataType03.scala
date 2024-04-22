package com.zishi.scala.a01.okk02

import com.zishi.scala.Student


object Test07_DataType03 {
  def main(args: Array[String]): Unit = {
    //5、空类型
    //5.1、空值Unit
    def m1(): Unit = {
      println("m1被调用执行")
    }

    val a: Unit = m1()
    println("a: " + a)

    //5.2、空引用Null
    //val n: Int = null  //error
    var student: Student = new Student("alice", 20)
    student = null
    println(student)

    //5.3、Nothing，nothing不会正常返回
    def m2(n: Int): Int = {
      if (n == 0)
        throw new NullPointerException
      else
        return n
    }

    val b: Int = m2(2)
    println("b: " + b)
  }
}