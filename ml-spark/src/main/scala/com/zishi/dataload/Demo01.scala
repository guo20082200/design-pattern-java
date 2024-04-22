package com.zishi.dataload

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object Demo01 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("SparkDemo")
      .master("local[4]")
      .getOrCreate()

    val df = spark.read.format("image").option("dropInvalid", true).load("data/mllib/images/origin/kittens")
  }

}
