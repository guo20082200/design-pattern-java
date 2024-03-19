package com.zishi.scala

class Student(name: String, var age: Int) {
  def printInfo(): Unit = {
    println(name + " " + age + " " + Student.school)
  }
}

//引入伴生对象
object Student {
  val school: String = "peking"

  def main(args: Array[String]): Unit = {
    val alice = new Student("alice", 222)
    val bob = new Student("bob", 333)

    alice.printInfo()
    bob.printInfo()
  }
}