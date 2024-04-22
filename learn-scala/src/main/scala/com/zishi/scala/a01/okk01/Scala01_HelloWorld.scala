package com.zishi.scala.a01.okk01

/**
 * package包，等同java中的package
 *
 * object 声明对象(单例)
 * scala是一个完全面向对象的语言，java屮的靜态语法不是面向对象的。
 * scala语言没有静态语法。java语言的静态操作在scala中该如何使用?
 * scala采用新的关键字object来模拟静态语法，可以通过对象名称实现静态作
 * 如果使用object关键字声明一个对象，那么在编译时同时也会编译出对应class文件
 *
 *
 * Scala01_HelloWorld :单例对象名称，同时也是类名
 *
 *
 *
 */
object Scala01_HelloWorld {

  /**
   * def : scala语言中声明方法的关键字
   * main :Scala语言程序入口方法
   * main(...): 小括号表示方法参数列表，可以有参数，也可以没有参数，如果有多个参数，使用逗号隔开
   * args:Array[String]:方法参数
   *
   * Array[String]:表示参数类型
   * scala语言是一个完全面向对象的语言，所以数组也是对象。也有自己的类型:Array scala语言中中括号中的String表示泛型
   *
   *
   *  def方法名称(参数名称: 参数类型): 返回值类型 = { 方法体 }
   *
   * def main :Unit scala语言中方法的声明也符合scala的规则
   * 方法名 (参数列表):方法类型
   * scala语言是基于java开发，是一个完全面向对象语言。
   *
   * Unit： 返回值类型，这里等同于Java中的Void
   * =： 赋值
   * {}： 方法体
   *
   * @param args
   */
  def main(args: Array[String]): Unit = {
    // 方法体
    println("Hello Scala")

  }
}
