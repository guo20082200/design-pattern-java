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
