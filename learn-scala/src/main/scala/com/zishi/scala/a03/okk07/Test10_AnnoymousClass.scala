package com.zishi.scala.a03.okk07

/**
 * 匿名内部类，和Java一样，重写所有抽象字段和⽅法
 */
object Test10_AnonymousClass {
  def main(args: Array[String]): Unit = {
    val person: Person10 = new Person10 {
      override var name: String = "alice"

      override def eat(): Unit = println("person eat")
    }
    println(person.name)
    person.eat()
  }
}

// 定义抽象类
abstract class Person10 {
  var name: String

  def eat(): Unit
}