package com.zishi.scala.a03.enc

import scala.beans.BeanProperty

/**
 * 回顾java中，如果是public向外公开的，那么必须和文件名⼀致，也只能有⼀个。不写访问修饰符则可以定义多个，包访问权限。
 * scala中没有public关键字，默认就是公有，不能加public，⼀个文件可以写多个类，不要求和文件名⼀致。
 *
 * 类的定义格式：
 * [descriptor] class classname {
 * // body: fields & methods
 * [descriptor] var/val name: Type = _
 * [descriptor] method(args: ArgsType): RetType = {
 * // method body
 * }
 * }
 */
object Test03_Class {
  def main(args: Array[String]): Unit = {
    //创建一个对象
    val student = new Student()
    //student.name // error, 不能访问private属性
    println(student.age)
    println(student.sex)
    student.sex = "female"
    println(student.sex)

    student.setAge(123)

    println(student.getAge)
    // println(student.name) // Symbol name is inaccessible from this place
  }
}

/**
 * 定义一个类
 * 访问修饰符可以是：private protected private [pacakgeName]，默认就是公有，不需要加。
 */
class Student {
  //定义属性
  private var name: String = "alice"

  /**
   * 成员如果需要Java Bean规范的getter和setter的话可以加
   *
   * @scala.beans.BeanProperty相当于⾃动创建，不需要显式写出。
   */
  @BeanProperty
  var age: Int = _

  /**
   * 成员给初值:
   * _ 会赋默认值，scala中定义变量必须赋值，可以这样做。值类型的值0，引⽤则是null。
   * 定义常量的话不能⽤_，因为只能初始化⼀次，编译器会提⽰。
   */
  var sex: String = _
}

/**
 * 封装：
 * Java的封装：私有化，提供getter和setter。
 * scala中考虑到Java太冗余了，脱裤⼦放屁⼀样。scala中的公有属性，底层实际为private，并通过get⽅
 * 法obj.field()和set⽅法obj.field_=(value)对其进⾏操作。所以scala不推荐设置为private。
 * 如果需要和其他框架互操作，必须提供Java Bean规范的getter和setter的话可以加 @scala.beans.BeanProperty注解。
 *
 * 访问权限：
 * Java中private protected public和默认包访问权限。
 * scala中属性和⽅法默认公有，并且不提供public关键字。
 * private私有，类内部和伴⽣对象内可⽤。
 * protected保护权限，scala中比java中严格，只有同类、⼦类可访问，同包⽆法访问。【因为java中说实话有点奇怪】
 * private [pacakgeName]增加包访问权限，在包内可以访问。
 */
class Dog {
  //定义属性
  private var name: String = "大黄"
  @BeanProperty
  var age: Int = _
  var sex: String = _
}