package com.zishi.dataload.ss02

import org.apache.spark.sql.SparkSession

object Demo01 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("SparkDemo")
      .master("local[4]")
      .getOrCreate()


    spark.close()

  }

}
