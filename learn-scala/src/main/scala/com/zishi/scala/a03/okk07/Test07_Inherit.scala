package com.zishi.scala.a03.okk07

/**
 * 继承：
 * 1. class ChildClassName[(argList1)] extends BaseClassName[(args)] { body }
 * 2. ⼦类继承⽗类属性和⽅法。
 * 3. 可以调⽤⽗类构造器，但感觉好像很局限，
 * 4.
 */
object Test07_Inherit {
  def main(args: Array[String]): Unit = {
    /**
     * 1. 先调用父类主构造器
     * 2. 再调用父类辅助构造器
     * 3. 再调用子类主构造器
     * 4. 再调用子类辅助构造器
     */
    val student1: Student7 = new Student7("alice", 18)
    val student2 = new Student7("bob", 20, "std001")

    student1.printInfo()
    student2.printInfo()

    /**
     * 1. 先调用父类主构造器
     * 2. 再调用子类主构造器
     */
    val teacher = new Teacher
    teacher.printInfo()

    def personInfo(person: Person7): Unit = {
      person.printInfo()
    }

    println("=========================")

    // 1. 父类的主构造器调用
    val person = new Person7
    personInfo(student1)
    personInfo(teacher)
    personInfo(person)
  }
}

// 定义一个父类
class Person7() {
  var name: String = _
  var age: Int = _

  println("1. 父类的主构造器调用")

  def this(name: String, age: Int) {
    this()
    println("2. 父类的辅助构造器调用")
    this.name = name
    this.age = age
  }

  def printInfo(): Unit = {
    println(s"Person: $name $age")
  }
}

// 定义子类
class Student7(name: String, age: Int) extends Person7(name, age) {
  var stdNo: String = _

  println("3. 子类的主构造器调用")

  def this(name: String, age: Int, stdNo: String) {
    this(name, age)
    println("4. 子类的辅助构造器调用")
    this.stdNo = stdNo
  }

  override def printInfo(): Unit = {
    println(s"Student: $name $age $stdNo")
  }
}

class Teacher extends Person7 {
  println("1. Teacher类的主构造器调用")
  override def printInfo(): Unit = {
    println(s"Teacher")
  }
}