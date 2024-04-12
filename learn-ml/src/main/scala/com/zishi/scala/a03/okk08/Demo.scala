package com.zishi.scala.a03.okk08

object Demo {

  def main(args: Array[String]): Unit = {

    import com.zishi.scala.a03.okk07.Person
    val person = new Person()
    println(person.age) // // 这里可以访问私有变量
  }
}
