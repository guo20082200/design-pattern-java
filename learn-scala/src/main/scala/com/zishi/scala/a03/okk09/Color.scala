package com.zishi.scala.a03.okk09

object Color extends Enumeration {


  // 自动赋值枚举成员
  val Red, Green, eeee = Value
  /*
  * 相当于分别初始化：
  * val Red = Value
  * val Green = Value
  * val Blue = Value
  */

  // 手动使用 Value(id: Int, name: String) 方法手动进行id和name的设置
  // 使用重载有參版本的Value(id: Int, name: String)不能一次性给多个枚举成员赋值，会编译报错(id冲突)
  val White = Value(100, "white")
  val Black = Value(200, "black")

}

object TestEnumeration extends App {


  Color.values foreach { color =>
    println(s"ID: ${color.id}, Str: $color")
  }

}