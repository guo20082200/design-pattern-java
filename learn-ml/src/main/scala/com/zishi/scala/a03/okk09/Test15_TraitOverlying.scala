package com.zishi.scala.a03.okk09

object Test15_TraitOverlying {
  def main(args: Array[String]): Unit = {
    val student = new Student15
    student.increase()

    // 钻石问题特征叠加
    val myFootBall = new MyFootBall
    println(myFootBall.describe())
  }
}

trait Knowledge15 {
  var amount: Int = 0

  def increase(): Unit = {
    println("knowledge increased")
  }
}

trait Talent15 {
  def singing(): Unit

  def dancing(): Unit

  def increase(): Unit = {
    println("talent increased")
  }
}

class Student15 extends Person13 with Talent15 with Knowledge15 {
  override def dancing(): Unit = println("dancing")

  override def singing(): Unit = println("singing")

  override def increase(): Unit = {
    super[Person13].increase()
  }
}

trait Ball {
  def describe(): String = "ball"
}

trait ColorBall extends Ball {
  private var color: String = "red"

  override def describe(): String = color + "_" + super.describe()
}

trait CategoryBall extends Ball {
  private var category: String = "foot"

  override def describe(): String = category + "_" + super.describe()
}

// equals to MyFootBall -> ColorBall -> CategoryBall -> Ball
class MyFootBall extends CategoryBall with ColorBall {
  override def describe(): String = super.describe()
}