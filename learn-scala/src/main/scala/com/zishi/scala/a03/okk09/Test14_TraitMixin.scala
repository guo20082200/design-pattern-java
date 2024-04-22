package com.zishi.scala.a03.okk09

/**
 * 特质的混入：
 * 1. 有⽗类class extends baseClass with trait1 with trait2 ... {}
 * 2. 没有⽗类class extends trait1 with trait2 ... {}
 *
 * 3. 其中可以定义抽象和非抽象的属性和⽅法。
 * 4. 匿名⼦类也可以引入特征。
 *
 * 5. 特征和基类或者多个特征中重名的属性或⽅法需要在⼦类中覆写以解决冲突，最后因为动态绑定，所有
 * 使⽤的地⽅都是⼦类的字段或⽅法。属性的话需要类型⼀致，不然提⽰不兼容。⽅法的话参数列表不⼀致会视为重载⽽不是冲突。
 * 6. 如果基类和特征中的属性或⽅法⼀个是抽象的，⼀个非抽象，且兼容，那么可以不覆写。很直观，就是不能冲突不能⼆义就⾏。
 * 7. 多个特征和基类定义了同名⽅法的，就需要在⼦类重写解决冲突。其中可以调⽤⽗类和特征的⽅法，此
 * 时super.methodName指代按照顺序最后⼀个拥有该⽅法定义的特征或基类。也可以⽤
 * super[baseClassOrTraitName].methodName直接指代某个基类的⽅法，注意需要是直接基类，间接基类则不⾏。
 * 8. 也就是说基类和特征基本是同等地位。
 */
object Test14_TraitMixin {
  def main(args: Array[String]): Unit = {
    val student = new Student14
    student.study()
    student.increase()

    student.play()
    student.increase()

    student.dating()
    student.increase()

    println("===========================")
    // 匿名⼦类也可以引入特征。动态混入
    val studentWithTalent = new Student14 with Talent {
      override def dancing(): Unit = println("student is good at dancing")

      override def singing(): Unit = println("student is good at singing")
    }

    studentWithTalent.sayHello()
    studentWithTalent.play()
    studentWithTalent.study()
    studentWithTalent.dating()
    studentWithTalent.dancing()
    studentWithTalent.singing()
  }
}

// 再定义一个特质
trait Knowledge {
  var amount: Int = 0

  def increase(): Unit
}


class Student14 extends Person13 with Young with Knowledge {
  // 重写冲突的属性
  override val name: String = "student"

  // 实现抽象方法
  def dating(): Unit = println(s"student $name is dating")

  def study(): Unit = println(s"student $name is studying")

  // 重写父类方法
  override def sayHello(): Unit = {
    super.sayHello()
    println(s"hello from: student $name")
  }

  // 实现特质中的抽象方法
  override def increase(): Unit = {
    amount += 1
    println(s"student $name knowledge increased: $amount")
  }
}

