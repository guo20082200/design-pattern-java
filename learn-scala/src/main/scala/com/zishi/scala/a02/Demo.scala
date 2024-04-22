package com.zishi.scala.a02

object Demo {

  def main(args: Array[String]): Unit = {

    import com.zishi.scala.a03.okk07.Person
    val person = new Person()
    // println(person.age) // 这里无法访问私有变量
  }
}
