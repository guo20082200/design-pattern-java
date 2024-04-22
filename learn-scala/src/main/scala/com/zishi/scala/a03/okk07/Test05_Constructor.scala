package com.zishi.scala.a03.okk07

/**
 * 构造器的格式：
 * class ClassName [descriptor] [([descriptor][val/var] arg1: Arg1Type, [descriptor][val/var] arg2: ...)] {
 *    // 主构造器, 只有一个, like record in java
 *  // 辅助构造器
 *  def this(argsList1) {
 *    this(args) // 调用主构造器
 *  }
 *  def this(argsList2) { // 构造器重载
 *    this(argsList1) // 可以直接调用主构造器或者调用其他辅助构造器(直接或间接调用了主构造器)
 * }
 *
 * 1. 辅助构造器必须调用主构造器
 */
object Test05_Constructor {
  def main(args: Array[String]): Unit = {
    val student1 = new Student1
    student1.Student1()

    val student2 = new Student1("alice")
    val student3 = new Student1("bob", 25)
  }
}

// 定义一个类
class Student1() {
  // 定义属性
  var name: String = _
  var age: Int = _

  println("1. 主构造方法被调用")

  //声明辅助构造方法
  def this(name: String) {
    this() //直接调用主构造器
    println("2. 辅助构造方法一被调用")
    this.name = name
    println(s"name: $name age: $age")
  }

  def this(name: String, age: Int) {
    this(name)
    println("3. 辅助构造方法二被调用")
    this.age = age
    println(s"name: $name age: $age")
  }

  def Student1(): Unit = {
    println("一般方法被调用")
  }
}