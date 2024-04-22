package com.zishi.scala.a03.enc

/**
 * 访问权限：
 * Java中private protected public和默认包访问权限。
 * scala中属性和⽅法默认公有，并且不提供public关键字。
 * private私有，类内部和伴⽣对象内可⽤。
 * protected保护权限，scala中比java中严格，只有同类、⼦类可访问，同包⽆法访问。【因为java中说实话有点奇怪】
 * private [pacakgeName]增加包访问权限，在包内可以访问。
 */
object Test04_ClassForAccess02 {

  def main(args: Array[String]): Unit = {
    // scala中属性和⽅法默认公有，并且不提供public关键字。
    val person = new Person2
    println(person.sex) // 默认公有
    println(person.printInfo())  // 默认公有
    // private私有，类内部和伴⽣对象内可⽤。
    // protected保护权限，scala中比java中严格，只有同类、⼦类可访问，同包⽆法访问。【因为java中说实话有点奇怪】
    // println(person.name) // 同包无法访问 Symbol name is inaccessible from this place
    // private [pacakgeName]增加包访问权限，在包内可以访问。
    println(person.age)
  }
}

// 定义一个父类
class Person2 {
  private var idCard: String = "3523566"
  protected var name: String = "alice"
  var sex: String = "female"
  private[com] var age: Int = 18

  def printInfo(): Unit = {
    // 类内部使用private变量
    // 类内部使用protected变量
    println(s"Person: $idCard $name $sex $age")
  }
}

class Person3 extends Person2 {
  override def printInfo(): Unit = {
    // 类内部使用private变量
    // 可以使用$name，⼦类可访问父类的protected变量
    println(s"Person: $name $sex $age")
  }
}

/**
 * object Person 为 class Person的伴生对象
 */
object Person2 {
  def main(args: Array[String]): Unit = {
    val person = new Person2
    println(person.idCard) // 伴⽣对象内使用类的私有变量
    println(person.name) // 伴⽣对象内使用类的protected变量
  }
}