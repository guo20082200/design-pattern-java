package com.zishi.scala.a02.okk05

/**
 * 函数至简原则注意事项
 */
object Test08_Practice02 {
  def main(args: Array[String]): Unit = {

    def test(name: String, f: String => Unit): Unit = {
      f(name)
    }

    def func(name: String): Unit = {
      println("name:" + name)
    }

    // 1. 调用
    test("zbc", func)

    // 2. 化简
    test("zbc", (name: String) => println("name:" + name))

    //3， 化简
    test("zbc", name => println("name:" + name))

    //4， 下面的化简不对
    // _ 代表第二个参数，第二个参数是函数对象，并不是函数对象的参数
    test("zbc", _ => println("name:" + _)) //输出结果为：com.zishi.scala.a02.okk05.Test08_Practice02$$$Lambda$18/0x000000080009d840@2ea6137

    // 报错：type mismatch, 返回的是Unit
    // 这是因为这里使用的函数的嵌套。不确定下划线 _ 是哪层函数的简化
    // test("zbc",println("name:" + _))

    // 做个如下变形


    def test2(f: String => Unit): Unit = {
      f("测试变形")
    }

    def func2(name: String): Unit = {
      println("func2:" + name)
    }
    // 1. 化简
    test2((name: String) => println("func2:" + name))

    // 2. 化简
    test2(name => println("func2:" + name))

    //3. 简化
    test2(name => println(name))
    test2(println(_))
    test2(println _)
    test2(println)
  }
}