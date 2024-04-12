package com.zishi.scala.a03

/**
 * 创建包对象
 */
package object MyPackageObject {

  //定义当前包共享的属性和方法
  val commonValue = "定义当前包共享的属性和方法"

  def sayHello(): Unit = {
    println("hh")
  }
}

和 Java 一样，可以在顶部使用 import 导入，在这个文件中的所有类都可以使用。
局部导入：什么时候使用，什么时候导入。在其作用范围内都可以使用。
通配符导入：import java.util._
给类起名：import java.util.{ArrayList=>JL}
导入相同包的多个类：import java.util.{HashSet, ArrayList}
屏蔽类：import java.util.{ArrayList =>_,_}
导入包的绝对路径：new _root_.java.util.HashMap