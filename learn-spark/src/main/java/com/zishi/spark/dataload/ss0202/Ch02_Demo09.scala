//package com.zishi.dataload.ss0202
//
//import org.apache.spark.{SparkConf, SparkContext}
//
///**
// * 生成数据样本
// */
//object Ch02_Demo09 {
//
//  def main(args: Array[String]): Unit = {
//    /**
//     * val spark = SparkSession.builder()
//     * .appName("SparkDemo")
//     * .master("local[4]")
//     * .getOrCreate()
//     */
//    val conf = new SparkConf().setAppName("SummaryStatisticsExample").setMaster("local[4]")
//    val sc = new SparkContext(conf)
//
//
//    /**
//     * 1. KMeansDataGenerator.generateKMeansRDD()
//     * 2. LinearDataGenerator.generateLinearRDD()
//     * 3. LogisticRegressionDataGenerator.generateLogisticRDD()
//     * 4. SVMDataGenerator
//     * 5. MFDataGenerator
//     */
//
//
//    /**
//     * 1. KMeansDataGenerator.generateKMeansRDD()
//     * 用于生成KMeans的训练样本数据，格式为RDD[Array[Double]]
//     */
//    println("...............KMeansDataGenerator.generateKMeansRDD()..................")
//    val ailx_kmeans_rdd = KMeansDataGenerator.generateKMeansRDD(sc, 10, 2, 5, 1.0, 3)
//
//    for (i <- ailx_kmeans_rdd) {
//      for (j <- i) {
//        print(j + " ")
//      }
//      println()
//    }
//
//    /**
//     * 2. LinearDataGenerator.generateLinearRDD()
//     * 用于生成线性回归的训练样本数据，格式为RDD[LabeledPoint]
//     */
//    println("...............LinearDataGenerator.generateLinearRDD()..................")
//    val ailx_linear_rdd = LinearDataGenerator.generateLinearRDD(sc, 10, 3, 1.0, 1, 0)
//
//    ailx_linear_rdd.foreach(println)
//
//
//    /**
//     * 3. LogisticRegressionDataGenerator.generateLogisticRDD()
//     * 用于生成逻辑回归的训练样本数据，格式为RDD[LabeledPoint]
//     */
//    println("...............LogisticRegressionDataGenerator.generateLogisticRDD()..................")
//    val ailx_logistic_rdd = LogisticRegressionDataGenerator.generateLogisticRDD(sc, 10, 3, 1.0, 1, 0.5)
//    ailx_logistic_rdd.foreach(println)
//
//    sc.stop()
//
//  }
//
//}
//
