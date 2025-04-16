package com.zishi.dataload

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object Demo01 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("SparkDemo")
      .master("local[4]")
      .getOrCreate()

    // [PATH_NOT_FOUND] Path does not exist: file:/D:/my-learn/design-pattern-java/data/mllib/images/origin/kittens.
    val df = spark.read.format("image").option("dropInvalid", true).load("ml-spark/data/mllib/images/origin/kittens")

    println(".............." + df.first())

    spark.close()

  }

}
