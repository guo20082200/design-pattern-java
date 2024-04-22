package com.zishi.scala.a03.okk07

/**
 * 抽象类：
 * 1. abstract calss ClassName
 * 2. 抽象属性：val/var name: Type，不给定初始值。
 * 3. 抽象⽅法：def methodName(): RetType，只声明不实现。
 * 4. ⼦类如果没有覆写全部⽗类未定义的属性和⽅法，那么就必须定义为抽象类。
 * 5. 重写非抽象⽅法属性必须加override，重写抽象⽅法则可以不加override。
 * 6. ⼦类调⽤⽗类中⽅法使⽤super关键字。
 * 7. ⼦类重写⽗类抽象属性，⽗类抽象属性可以⽤var修饰，val var都可以。因为⽗类没有实现嘛，需要到⼦类中来实现。
 * 8. 如果是重写非抽象属性，则⽗类非抽象属性只⽀持val，不⽀持var。因为var修饰为可变量，⼦类继承后可以直接使⽤修改，没有必要重写。val不可变才有必要重写。
 * 9. 实践建议是重写就加override，都是很⾃然的东⻄。
 */
object Test09_AbstractClass {
  def main(args: Array[String]): Unit = {
    val student = new Student9
    student.eat()
    student.sleep()
  }
}

// 定义抽象类
abstract class Person9 {
  // 非抽象属性
  var name: String = "person"

  //8. 如果是重写非抽象属性，则⽗类非抽象属性只⽀持val，不⽀持var。因为var修饰为可变量，⼦类继承后可以直接使⽤修改，没有必要重写。val不可变才有必要重写。
  val sex: String = "male"

  // 抽象属性
  var age: Int

  // 非抽象方法
  def eat(): Unit = {
    println("person eat")
  }

  // 抽象方法
  def sleep(): Unit
}

// 定义具体的实现子类
class Student9 extends Person9 {
  // 实现抽象属性和方法 override可加可不加
  override var age: Int = 18

  /**
   * 重写抽象方法，override可加可不加
   */
  override def sleep(): Unit = {
    println("student sleep")
  }

  // 重写非抽象属性和方法, 因为var修饰为可变量，⼦类继承后可以直接使⽤修改，没有必要重写
  name = "student"

  // val不可变才有必要重写。
  override val sex: String = "female"
  /**
   * 重写非抽象方法，override必须加
   */
  override def eat(): Unit = {
    super.eat()
    println("student eat")
  }
}