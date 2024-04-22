//package com.zishi.scala.a03.okk09
//
///**
// * 自身类型：
// *
// */
//object Test16_TraitSelfType {
//  def main(args: Array[String]): Unit = {
//    val user = new RegisterUser("alice", "123456")
//    user.insert()
//  }
//}
//
//// 用户类
//class User(val name: String, val password: String)
//
//trait UserDao {
//  /**
//   * 下面 this: User => 中的 this 只是一个别名，
//   * 可以是 self 或任何有效标识(如 abc: User)，这里使用 this 等于未指定别名，
//   * 与原本的 this 重叠了。
//   */
//  //this : User => //这个表示 UserDao 本身也必须是一个 User，它的子类型必须想办法符合这一要求
//
//  /**
//   * 如果声明为非 this 的话则可以看下面这段代码:
//   * 表示： this 永远都在，同时 abc 与 this 是完全一样的效果。
//   */
//  abc : User => //  和上面的 this : User => 定义 等价
//  // 向数据库插入数据
//  def insert(): Unit = {
//    println(s"insert into db: ${this.name}")
//  }
//}
//
//// 定义注册用户类
//class RegisterUser(name: String, password: String) extends User(name, password) with UserDao
//// class RegisterUser2 extends UserDao //报错： Illegal inheritance, self-type RegisterUser2 does not conform to User
//
//
//// self-type 可以指定多重类型，那么声明子类型或创建实例时也要符合多种类型
//trait User2 {
//  def res1(): Unit = println("User")
//}
//
//trait Animal{
//  def res2(): Unit = println("Animal")
//}
//
//trait Tweeter {
//  // self-type 可以指定多重类型，那么声明子类型或创建实例时也要符合多种类型
//  this: User2 with Animal =>
//  def res3(): Unit = println("Tweeter")
//}
//
//class VerifiedTweeter extends Tweeter with User2 with Animal
//// class VerifiedTweeter2 extends Tweeter with User2 // Illegal inheritance, self-type VerifiedTweeter2 does not conform to User2 with Animal
////直接实例化
//val t = new Tweeter with User2 with Animal
//t.res3()
//
//
//
////还有一种结构类型的 self-type, 更像是匿名类型的 self-type, 不是指定类型名而是类型必须实现的方法，例如：
//import scala.language.reflectiveCalls
//trait Person {
//  this: {def say() : Unit} =>  //这个语法后不能用大括号括起后续的代码
//  def foo(): Unit = say()  //Advanced language feature: reflective call
//}
//
///**
// * 定义中声明了： this: {def say: Unit} =>
// * 要求在 Tweeter 中必须实现该方法，如:
// */
//class Tweeter3 extends Person {
//  // 必须实现该方法
//  def say(): Unit = {
//    println("say something")
//  }
//}
//
////或者创建匿名的 Person 类及实例
//val person = new Person {
//  // 必须实现该方法
//  def say(): Unit = {
//    println("say something")
//  }
//  override def foo(): Unit = super.foo()
//}
//
//
//
//
//
//
//
//
//
