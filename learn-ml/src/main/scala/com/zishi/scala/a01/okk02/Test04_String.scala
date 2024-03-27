package com.zishi.scala.a01.okk02

/**
 * 模板字符串：
 * s""：前缀s模板字符串，前缀f格式化模板字符串，通过$获取变量值，%后跟格式化字符串。
 * f""：前缀s模板字符串，前缀f格式化模板字符串，通过$获取变量值，%后跟格式化字符串。
 * raw""：不作格式化处理，按照原始样子输出。
 */
object Test04_String {
  def main(args: Array[String]): Unit = {
    //（1）字符串，通过+号连接
    val name: String = "alice"
    val age: Int = 18
    println(age + "岁的" + name + "在smmmca学习")

    // *用于将一个字符串复制多次并拼接
    println(name * 3)

    //（2）printf用法：字符串，通过%传值。
    printf("%d岁的%s在cxxx学习", age, name)
    println()
    println()

    //（3）字符串模板（插值字符串）：通过$获取变量值
    println(s"${age}岁的${name}在cxxx学习")

    val num: Double = 2.3456
    println(f"The num is ${num}%2.2f") //格式化模板字符串
    println(raw"The num is ${num}%2.2f")//raw不作格式化处理

    //三引号表示字符串，保持多行字符串的原格式输出
    val sql =
      s"""
         |select *
         |from
         |  student
         |where
         |  name = ${name}
         |and
         |  age > ${age}
         |""".stripMargin
    println(sql)
  }
}