package com.zishi.scala.a03.okk10

/**
 * 在Scala中，trait也可以继承class。特质会将class中的成员都继承下来。
 */
object 特质继承类 {
  //1. 定义Message类. 添加printMsg()方法, 打印"测试数据..."
  class Message {
    def printMsg(): Unit = println("学好Scala，干好Spark！")
  }
  //2. 创建Logger特质，继承Message类.
  trait Logger extends Message
  //3. 定义ConsoleLogger类, 继承Logger特质.
  class ConsoleLogger extends Logger

  def main(args: Array[String]): Unit = {
    //4. 创建ConsoleLogger类的对象, 并调用printMsg()方法.
    val cl = new ConsoleLogger
    cl.printMsg()
  }

}
