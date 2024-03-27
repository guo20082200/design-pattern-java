package com.zishi.scala.a02.okk05

object Test03_FunctionParameter {
  def main(args: Array[String]): Unit = {
    //    （1）可变参数
    def f1(str: String*): Unit = {
      println(str)
    }

    f1("alice") // ArraySeq(alice)
    f1("aaa", "bbb", "ccc") // ArraySeq(aaa, bbb, ccc)

    //    （2）如果参数列表中存在多个参数，那么可变参数一般放置在最后
    def f2(str1: String, str2: String*): Unit = {
      println("str1: " + str1 + "，str2: " + str2)
    }

    // 可变参数不确定，底层实现的方式也不一样
    f2("alice") // str1: alice，str2: List()
    f2("aaa", "bbb", "ccc") // str1: aaa，str2: ArraySeq(bbb, ccc)

    //    （3）参数默认值，一般将有默认值的参数放置在参数列表的后面
    def f3(name: String = "eeeee"): Unit = {
      println("My school is " + name)
    }

    f3("school")
    f3()

    //    （4）带名参数
    def f4(name: String = "eeeee", age: Int): Unit = {
      println(s"${age}岁的${name}在smmmca学习")
    }

    f4("alice", 20)
    f4(age = 23, name = "bob")
    f4(age = 21)
  }
}