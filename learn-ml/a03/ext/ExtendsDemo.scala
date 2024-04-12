package com.zishi.scala.a03.ext

import scala.beans.BeanProperty

/**
 * 继承：
 * class ChildClassName[(argList1)] extends BaseClassName[(args)] { body }
 * ⼦类继承⽗类属性和⽅法。
 * 可以调⽤⽗类构造器，但感觉好像很局限，⼦类中只可能调⽤到主构造或者辅助构造中的其中⼀个构造器。
 * 那如果⽗类有多种构造⽅式，⼦类想继承也没有办法？只能是其中⼀种。
 * 不考虑太多负担，按照scala惯⽤写法来写起来还是挺轻松的。
 */
object ExtendsDemo {

}


class Animal {
  @BeanProperty
  private var name = ""

  def say(): Unit = {
    println("Animal")
  }
}


class Dog extends Animal {

}