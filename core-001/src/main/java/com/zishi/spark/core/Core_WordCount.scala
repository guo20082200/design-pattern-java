package com.zishi.spark.core

import org.apache.spark.launcher.JavaModuleOptions
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object Core_WordCount {

  def main(args: Array[String]): Unit = {
    //println(JavaModuleOptions.defaultModuleOptions)
    val sparkConf = new SparkConf().setAppName("word_count").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    //println(sc)

    // word count
    //1. read file
    val path = "adatas/test_WordCount.txt"
    val result = sc.textFile(path) // 读取文件
      .flatMap(_.split(" ")) // 按照空格对一行数据进行切割
      .map((_,1)) // 将key转换为一个tuple，
      .reduceByKey(_+_) //相同的key，对value进行reduce处理

    result.foreach(println)

    //val session = SparkSession.builder().master("local-cluster[8,2,1024]").appName("WC").getOrCreate()

    // 关闭连接
    sc.stop()

  }
}
