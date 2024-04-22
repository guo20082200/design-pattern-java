package com.zishi.scala.a03.okk07

/**
 * 多态：
 * 1. java中属性静态绑定，根据变量的引⽤类型确定，⽅法是动态绑定。
 * 2. 但scala中属性和⽅法都是动态绑定。就属性⽽⾔，其实也不应该在⼦类和⽗类中定义同名字段。
 * 3. 同java⼀样，所有实例⽅法都是虚⽅法，都可以被⼦类覆写。
 * 4. override关键字覆写。
 * 5. scala中属性（字段）也可以被重写，加override关键字。
 */
object Test08_DynamicBind {
  def main(args: Array[String]): Unit = {
    val student: Person8 = new Student8
    println(student.name)
    student.hello()
  }
}

class Person8 {
  val name: String = "person"

  def hello(): Unit = {
    println("hello person")
  }
}

class Student8 extends Person8 {
  override val name: String = "student"

  override def hello(): Unit = {
    println("hello student")
  }
}