package com.zishi.sp.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, Partitioner, RangePartitioner, SparkConf, SparkContext}


/**
 * RDD的分区器
 */
object Rdd_Demo09 {


  /**
   * HashPartitioner：
   * 原理：通过键的哈希值对分区数取模来决定数据的分配。
   *
   * 特点：
   *  分区过程简单高效。
   *  适合数据键分布较均匀的场景。
   * 使用场景：
   *  默认分区器，用于大多数键值对 RDD 算子（例如 reduceByKey、join 等）。
   *
   * @param sc
   */
  def rddPartitioner01(sc: SparkContext): Unit = {
    val rdd = sc.parallelize(Seq(("a", 1), ("b", 2), ("c", 3), ("d", 4)))
    val partitionedRDD = rdd.partitionBy(new HashPartitioner(3))
    println(partitionedRDD.partitions.length)  // 输出: 3
  }

  /**
   * RangePartitioner:
   * 原理：根据键的范围进行分区，通常对键进行排序后进行划分。
   *
   * 特点：
   *
   * 保证键的分区范围连续。
   *
   * 适合键具有排序关系的场景（如数值键或字符串键）。
   *
   * 使用场景：
   *
   * 用于分区数据排序处理。
   *
   * @param sc
   */
  def rddPartitioner02(sc: SparkContext): Unit = {
    val rdd = sc.parallelize(Seq(("a", 1), ("b", 2), ("c", 3), ("d", 4)))

    val partitionedRDD =  rdd.partitionBy(new RangePartitioner(3, rdd))

    println(partitionedRDD.partitions.length)  // 输出: 3
  }


  def rddPartitioner03(sc: SparkContext): Unit = {
    val rdd = sc.parallelize(Seq(("a", 1), ("b", 2), ("c", 3), ("d", 4)))

    val partitionedRDD =  rdd.partitionBy(new CustomPartitioner(3))

    println(partitionedRDD.partitions.length)  // 输出: 3
  }

  private class CustomPartitioner(partitions: Int) extends Partitioner {
    def numPartitions: Int = partitions

    def getPartition(key: Any): Int = {
      key match {
        case k: String if k.startsWith("a") => 0
        case k: String if k.startsWith("b") => 1
        case _ => 2
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Hello")
    val sc: SparkContext = new SparkContext(sparkConf)
    //rddPartitioner01(sc)
    //rddPartitioner02(sc)
    rddPartitioner03(sc)

    sc.stop()
  }
}
