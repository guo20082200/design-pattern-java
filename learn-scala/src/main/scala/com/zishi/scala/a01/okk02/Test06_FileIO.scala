package com.zishi.scala.a01.okk02

import java.io.{File, PrintWriter}
import scala.io.Source

object Test06_FileIO {
  def main(args: Array[String]): Unit = {
    //1、从文件中读取数据
    Source.fromFile("learn-ml/src/main/resources/log4j2.properties").foreach(print)//Source.fromFile()读文件、foreach()遍历操作
   // Source.fromFile("D:\\my-learn\\design-pattern-java\\learn-ml\\src\\main\\resources\\log4j2.properties").foreach(print)//Source.fromFile()读文件、foreach()遍历操作


    //2、将数据写入文件
    val writer = new PrintWriter(new File("learn-ml/src/main/resources/output.txt"))
    writer.write("hello scala from java writer！！！")
    writer.close()
  }
}