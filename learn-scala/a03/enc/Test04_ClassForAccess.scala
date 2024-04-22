package com.zishi.scala.a03.enc

object Test04_ClassForAccess {

  def main(args: Array[String]): Unit = {
    val person = new Person2
    println(person.age) // 访问 Person2的私有变量（增加了包访问权限的私有变量）
  }
}

// 定义一个父类
class Person {
  private var idCard: String = "3523566"
  protected var name: String = "alice"
  var sex: String = "female"
  private[enc] var age: Int = 18

  def printInfo(): Unit = {
    println(s"Person: $idCard $name $sex $age")
  }
}