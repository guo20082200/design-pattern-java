package com.zishi.scala.a03.okk07

/**
 * 构造器的参数：
 *
 * 特点：
 * 1. 主构造器写在类定义上，⼀定是构造时最先被调⽤的构造器，⽅法体就是类定义，可以在类中⽅法定义的同级编写逻辑，都是主构造器⼀部分，按顺序执⾏。
 * 2. 辅助构造器⽤this定义。
 * 3. 辅助构造器必须直接或者间接调⽤主构造器，调⽤其他构造必须位于第⼀⾏。
 * 4. 主构造器和辅助构造器是重载的⽅法，所以参数列表不能⼀致。
 * 5. 可以定义和类名同名⽅法，就是⼀个普通⽅法。
 *
 * 主构造器中形参三种形式：不使⽤任何修饰，var修饰，val修饰。
 * 1. 不使⽤任何修饰那就是⼀个形参，但此时在类内都可以访问到这个变量。逻辑上不是⼀个成员，但是可以访问
 * 2. 使⽤var val修饰那就是定义为类成员，分别是变量和常量，不需要也不能在类内再定义⼀个同名字段。调⽤时传入参数就直接给到该成员，不需要再显式赋值。
 * 3. 主构造器中的var val成员也可以添加访问修饰符。
 * 4. 不加参数列表相当于为空，()可以省略。
 * 5. 主构造器的访问修饰符添加到参数列表()前。
 *
 * 实践指南：
 * 1. 推荐使⽤scala⻛格的主构造器var val修饰参数的编写⽅法，⽽不要被Java毒害！
 * 2. 如果需要多种重载的构造器那么就添加新的的辅助构造器。
 */
object Test06_ConstructorParams {
  def main(args: Array[String]): Unit = {
    val student2 = new Student2
    student2.name = "alice"
    student2.age = 18
    println(s"student2: name = ${student2.name}, age = ${student2.age}")

    val student3 = new Student3("bob", 20)
    println(s"student3: name = ${student3.name}, age = ${student3.age}")

    val student4 = new Student4("cary", 25)
    //println(s"student4: name = ${student4.name}, age = ${student4.age}")
    student4.printInfo()

    val student5 = new Student5("bob", 20)
    println(s"student3: name = ${student5.name}, age = ${student5.age}")

    student3.age = 21

    val student6 = new Student6("cary", 25, "peking")
    println(s"student6: name = ${student6.name}, age = ${student6.age}")
    student6.printInfo()
  }
}

// 定义类
// 无参构造器
class Student2 {
  // 单独定义属性
  var name: String = _
  var age: Int = _
}

// 上面定义等价于
class Student3(var name: String, var age: Int)

// 主构造器参数无修饰
class Student4(name: String, age: Int) {
  def printInfo(): Unit = {
    println(s"student4: name = ${name}, age = $age")
  }
}

//class Student4(_name: String, _age: Int) {
//  var name: String = _name
//  var age: Int = _age
//}

class Student5(val name: String, val age: Int)

class Student6(var name: String, var age: Int) {
  var school: String = _

  def this(name: String, age: Int, school: String) {
    this(name, age)
    this.school = school
  }

  def printInfo(): Unit = {
    println(s"student6: name = ${name}, age = $age, school = $school")
  }
}