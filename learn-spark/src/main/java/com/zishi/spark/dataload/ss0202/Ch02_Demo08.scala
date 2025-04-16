package com.zishi.dataload.ss0202

import org.apache.spark.mllib.linalg.{Matrix, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.stat.{MultivariateStatisticalSummary, Statistics}
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * MLlib 数据格式
 */
object Ch02_Demo08 {

  def main(args: Array[String]): Unit = {
    /**
     * val spark = SparkSession.builder()
     * .appName("SparkDemo")
     * .master("local[4]")
     * .getOrCreate()
     */
    val conf = new SparkConf().setAppName("SummaryStatisticsExample").setMaster("local[4]")
    val sc = new SparkContext(conf)


    /**
     * 1. 加载数据
     * MLUtils: MLLib工具类，提供加载，保存和预处理数据方法
     *
     * 加载LibSVM数据格式的数据，返回RDD[LabeledPoint]
     * LabeledPoint的格式：
     * (label:Double, features:Vector), label代表标签，features代表特征向量
     *
     * LibSVM数据格式如下：
     * {
     * {
     * {label index1:value1 index2:value2}
     * label:代表标签，value1代表特征，index1代表特征位置索引
     * }
     * }
     */
    val data: RDD[LabeledPoint] = MLUtils.loadLibSVMFile(sc, "D:\\workspace\\eclipse_workspace\\ml-spark\\data\\mllib\\sample_libsvm_data.txt")

    println(data.take(1).mkString("Array(", ", ", ")"))

    /**
     * 2. saveAsLibSVMFile： 保存为libSVM格式的文件、
     * MLUtils.saveAsLibSVMFile()
     */

    //3. MLUtils.appendBias()



    sc.stop()

  }

}

