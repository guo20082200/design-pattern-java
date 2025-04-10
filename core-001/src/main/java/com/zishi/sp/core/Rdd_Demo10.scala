package com.zishi.sp.core

import org.apache.spark._


/**
 * RDD的累加器
 */
object Rdd_Demo10 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Hello")
    val sc: SparkContext = new SparkContext(sparkConf)

    sc.stop()
  }
}
