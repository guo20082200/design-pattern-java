package com.zishi.scala.a03.okk09

/**
 * 特质的混入：
 * 首先, Trait动态混入是什么?
 * 1. Trait除了可以在类声明时继承特质以外，还可以在构建对象时混入特质，扩展目标类的功能
 * 2. 此种方式也可以应用于对抽象类功能进行扩展
 * 3. 动态混入是 Scala 特有的方式 (java 没有动态混入)，可在不修改类声明/定义的情况下，扩展类的功能，非常的灵活，耦合性低 。
 * 4. 动态混入可以在不影响原有的继承关系的基础上，给指定的类扩展功能。
 * 5. 同时要注意动态混入时，如果抽象类有抽象方法，如何混入(内部类形式实现抽象方法)
 */
object Test14_TraitMixin02 {
  def main(args: Array[String]): Unit = {

    // Trait 方式二： 构建对象时混入特质，扩展目标类的功能,可以混入多个Trait
    // 该方式的优点：1. 在不修改类声明/定义的情况下，扩展类的功能，非常的灵活，耦合性低 。2. 不影响原有的继承关系
    val sheep: Sheep = new Sheep() with Logable with Talent {
      override def singing(): Unit = ???

      override def dancing(): Unit = ???

      override def log(): Unit = ???
    }
  }
}

trait Logable {
  def log(): Unit
}

trait Talent {
  def singing(): Unit

  def dancing(): Unit
}

//Trait 方式一： 在类声明时继承特质Trait
class Cat extends Talent {
  override def singing(): Unit = println("singing")
  override def dancing(): Unit = println("dancing")
}

class Sheep {

}

abstract class Animal extends Talent {

}


