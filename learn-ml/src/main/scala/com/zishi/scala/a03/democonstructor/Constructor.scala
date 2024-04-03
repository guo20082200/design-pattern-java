package com.zishi.scala.a03.democonstructor

/**
 * 构造器：
 * 包括主构造器和辅助构造器：
 *
 * class ClassName [descriptor] [([descriptor][val/var] arg1: Arg1Type, [descriptor]
 * [val/var] arg2: ...)] { // main constructor, only one, like record in java
 * // assist constructor
 * def this(argsList1) {
 * this(args) // call main constructor
 * }
 * def this(argsList2) { // overload constrcutor
 * this(argsList1) // can call main constructor or other constructor that
 * call main constructor directly or indirectly
 * }
 * }
 */
object Constructor {

  def main(args: Array[String]): Unit = {
    val p: Person = new Person() // call main constructor
    p.personInfo() // call Person.Person() method
    val p1 = new Person("alice")
    val p2 = new Person("bob", 25)
    p1.Person()

    val p3: Person = new Person // 省略括号
  }
}


/**
 * 特点：
 *    主构造器写在类定义上，⼀定是构造时最先被调⽤的构造器，⽅法体就是类定义，可以在类中⽅法定义的同级编写逻辑，都是主构造器⼀部分，按顺序执⾏。
 *    辅助构造器⽤this定义。
 *    辅助构造器必须直接或者间接调⽤主构造器，调⽤其他构造必须位于第⼀⾏。
 *    主构造器和辅助构造器是重载的⽅法，所以参数列表不能⼀致。
 *    可以定义和类名同名⽅法，就是⼀个普通⽅法。
 *
 * 主构造器中形参三种形式：不使⽤任何修饰，var修饰，val修饰。
 *    不使⽤任何修饰那就是⼀个形参，但此时在类内都可以访问到这个变量。逻辑上不是⼀个成员
 *    （ 报错信息这么写），但是可以访问，WTF？？？
 *    使⽤var val修饰那就是定义为类成员，分别是变量和常量，不需要也不能在类内再定义⼀个同名
 *    字段。调⽤时传入参数就直接给到该成员，不需要再显式赋值。
 *    主构造器中的var val成员也可以添加访问修饰符。
 *    不加参数列表相当于为空，()可以省略。
 *    主构造器的访问修饰符添加到参数列表()前。
 *
 * 实践指南：
 *    推荐使⽤scala⻛格的主构造器var val修饰参数的编写⽅法，⽽不要被Java毒害！
 *    如果需要多种重载的构造器那么就添加新的的辅助构造器。
 */
class Person {
  var name: String = _
  var age: Int = _
  println("call main constructor")

  def this(name: String) {
    this()
    // Auxiliary constructor must begin with call to 'this'  辅助构造器必须以调用this开始
    println("call assist constructor 1")
    this.name = name
    println(s"Person: $name $age")
  }

  def this(name: String, age: Int) {
    this(name)
    this.age = age
    println("call assist constructor 2")
    println(s"Person: $name $age")
  }

  /**
   * just a common method, not constructor
   * 可以定义和类名同名⽅法，就是⼀个普通⽅法。不建议这么做
   */
  def Person(): Unit = {
    println("call Person.personInfo() method")
  }

  def personInfo(): Unit = {
    println("call Person.personInfo() method")
  }
}


/**
 * 主构造器中形参三种形式：不使⽤任何修饰，var修饰，val修饰。
 * 主构造器中的var val成员也可以添加访问修饰符。
 * @param name
 */
class Dog(private var name: String) {
  var age: Int = _
  println("call main constructor")

  def this(name: String, age: Int) = {
    this(name)
    this.age = age
    println("call assist constructor 2")
    println(s"Dog: $name $age")
  }

  def Dog(): Unit = {
    println("call Dog.Dog() method")
  }
}