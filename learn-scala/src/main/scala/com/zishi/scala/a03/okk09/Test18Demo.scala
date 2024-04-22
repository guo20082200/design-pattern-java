package com.zishi.scala.a03.okk09

object Test18Demo {

  //1. 定义一个特质Logger, 添加log(msg:String)方法.
  private trait Logger {
    def log(msg: String)
  }

  //2. 定义一个特质Warning, 添加warn(msg:String)方法.
  private trait Warning {
    def warn(msg: String)
  }

  // object继承特质trait
  //3. 定义单例对象ConsoleLogger, 继承上述两个特质, 并重写两个方法.
  private object ConsoleLogger extends Logger with Warning {
    override def log(msg: String): Unit = println("控制台日志信息：" + msg)

    override def warn(msg: String): Unit = println("控制台警告信息：" + msg)
  }

  //main方法, 作为程序的入口
  def main(args: Array[String]): Unit = {
    //4. 调用ConsoleLogger单例对象中的两个方法.
    ConsoleLogger.log("我是一条普通的日志信息")
    ConsoleLogger.warn("我是一条警告日志信息")
  }
}
