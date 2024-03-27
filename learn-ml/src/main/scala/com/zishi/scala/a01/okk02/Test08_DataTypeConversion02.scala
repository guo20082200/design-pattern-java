package com.zishi.scala.a01.okk02

object Test08_DataTypeConversion02 {
  def main(args: Array[String]): Unit = {
    //2、强制类型转换
    //（1）将数据由高精度转换为低精度，就需要使用到强制转换
    val n1: Int = -2.9.toInt
    println("n1: " + n1)

    //（2）强转符号只针对于最近的操作数有效，往往会使用小括号提升优先级
    val n2: Int = 2.6.toInt + 3.7.toInt
    val n3: Int = (2.6 + 3.7).toInt
    println("n2: " + n2)
    println("n3: " + n3)

    //3、数值类型和String类型的转换
    // (1) 数值转String
    val n: Int = 27
    val s: String = n + ""
    println(s)

    // (2) String转数值
    val m: Int = "12".toInt
    val f: Float = "12.3".toFloat
    val f2: Int = "12.3".toDouble.toInt
    println(f2)
  }
}