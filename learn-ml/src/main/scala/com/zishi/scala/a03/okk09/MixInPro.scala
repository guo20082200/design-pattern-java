package com.zishi.scala.a03.okk09

object MixInPro {
  def main(args: Array[String]): Unit = {
    val name = "aee"
    println(s"ssss = ${name}")
    val mySQL = new MySQL6 with DB6 {
      // 特质中未被初始化的字段在具体的子类中必须被重写。
      override var sal = 5
    }

    //特质中可以定义具体字段，如果初始化了就是具体字段，如果不初始化就是抽象字段。
    // 混入该特质 的类就具有了该字段，字段不是继承，而是直接加入类，成为自己的字段。
    println(mySQL.opertype)
  }
}

trait DB6 {
  var sal: Int //抽象字段
  var opertype: String = "insert"

  def insert(): Unit
  = {
  }
}

class MySQL6 {}
