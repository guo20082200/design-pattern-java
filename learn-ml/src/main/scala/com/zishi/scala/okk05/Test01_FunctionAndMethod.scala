package com.zishi.scala.okk05

object Test01_FunctionAndMethod {

  /**
   * public void main(final String[] args) {
   *    sayHi$1("alice");  -- 这里调用的就是：sayHi$1
   *    this.sayHi("bob");
   *    String result = this.sayHello("cary");
   *    .MODULE$.println(result);
   * }
   *
   * @param args
   */
  def main(args: Array[String]): Unit = {

    /**
     * 定义函数
     * 该函数不能重载
     *
     * 编译之后的字节码为：
     * private static final sayHi$1(name: String): Unit =  {
     *  MODULE$.println((new StringBuilder(4)).append("hi, ").append(name).toString)
     * }
     * 增加了修饰符：private static final
     * 方法名被修改了：sayHi$1
     *
     * @param name
     */
    def sayHi(name: String): Unit = {
      println("hi, " + name)
    }

    // 不能重载  sayHi is already defined in the scope
    /*def sayHi(i: Int): Unit = {
      println("hi, " + i)
    }*/

    // 调用函数，不加任何的操作，默认调用函数
    // 如果没有函数，则调用方法
    sayHi("alice")  // 这里调用的是函数

    // 调用对象方法
    Test01_FunctionAndMethod.sayHi("bob")

    // 获取方法返回值
    val result = Test01_FunctionAndMethod.sayHello("cary")
    println(result)
  }

  // 定义对象的方法
  def sayHi(name: String): Unit = {
    println("Hi, " + name)
  }

  def sayHello(name: String): String = {
    println("Hello, " + name)
    return "Hello"
  }
}