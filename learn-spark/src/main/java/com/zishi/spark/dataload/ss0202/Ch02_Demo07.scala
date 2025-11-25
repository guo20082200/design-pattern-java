//package com.zishi.spark.dataload.ss0202
//
//import org.apache.spark.ml.linalg.{Matrix, Vectors}
//import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.rdd.RDD
//import org.apache.spark.sql.SparkSession
//
///**
// * 统计操作：Statistics
// */
//object Ch02_Demo07 {
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
//    // $example on$
//    val observations = sc.parallelize(
//      Seq(
//        Vectors.dense(1.0, 10.0, 100.0),
//        Vectors.dense(2.0, 20.0, 200.0),
//        Vectors.dense(3.0, 30.0, 300.0)
//      )
//    )
//
//    // 计算列统计信息
//    val summary: MultivariateStatisticalSummary = Statistics.colStats(observations)
//    println(summary.mean) // [2.0,20.0,200.0]
//    println(summary.max) // [3.0,30.0,300.0]
//    println(summary.min) // [1.0,10.0,100.0]
//    println(summary.variance) // [1.0,100.0,10000.0]
//    println(summary.numNonzeros) // 每一列中非零的元素的个数 [3.0,3.0,3.0]
//    println(summary.normL1) // [6.0,60.0,600.0]
//    println(summary.normL2) // [3.7416573867739413,37.416573867739416,374.16573867739413]
//
//
//
//    // 皮尔森相关系数
//    // $example on$
//    val seriesX: RDD[Double] = sc.parallelize(Array(1, 2, 3, 3, 5)) // a series
//    // must have the same number of partitions and cardinality as seriesX
//    val seriesY: RDD[Double] = sc.parallelize(Array(11, 22, 33, 33, 555))
//
//    // compute the correlation using Pearson's method. Enter "spearman" for Spearman's method. If a
//    // method is not specified, Pearson's method will be used by default.
//    val correlation: Double = Statistics.corr(seriesX, seriesY, "pearson")
//    println(s"Correlation is: $correlation") // Correlation is: 0.8500286768773004
//
//    import org.apache.spark.mllib.linalg.Vector
//
//    val data: RDD[Vector] = sc.parallelize(
//      Seq(
//        Vectors.dense(1.0, 10.0, 100.0),
//        Vectors.dense(2.0, 20.0, 200.0),
//        Vectors.dense(5.0, 33.0, 366.0))
//    ) // note that each Vector is a row and not a column
//
//    // 用皮尔森方法来计算相关系统矩阵，
//    // Use "spearman" for Spearman's method
//    // 如果方法的名称没有指定，那么Pearson方法将采用默认的方法
//    val correlationMatrix: Matrix = Statistics.corr(data, "pearson")
//    println(correlationMatrix.toString)
//
//
//    val x = Vectors.dense(1.0, 2.0)
//    val y = Vectors.dense(3.0, 4.0)
//
//    val c = Statistics.chiSqTest(x, y)
//    print(c)
//
//    // 假设检验: 统计学意义是什么？？
//    val result = Statistics.chiSqTest(Vectors.dense(1.0, 10.0, 100.0), Vectors.dense(2.0, 20.0, 200.0))
//    println(result)
//
//    sc.stop()
//
//  }
//
//}
//
