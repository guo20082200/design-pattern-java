package com.zishi.sp.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object Rdd_Demo02 {

  /**
   * partitionBy: 将数据按照指定Partitioner 重新进行分区。 Spark 默认的分区器是HashPartitioner
   *
   * @param sc 上下文
   */
  def rddPartitionBy(sc: SparkContext): Unit = {
    val rdd: RDD[(Int, String)] = sc.makeRDD(Array((1, "aaa"), (2, "bbb"), (3, "ccc")), 3)
    import org.apache.spark.HashPartitioner
    val rdd2: RDD[(Int, String)] = rdd.partitionBy(new HashPartitioner(2))
    val result = rdd2.mapPartitionsWithIndex((index, iter) => {
      iter.map(x => s"Partition $index: $x")
    })
    result.collect().foreach(println)
  }

  /**
   * reduceByKey： 可以将数据按照相同的Key 对Value 进行聚合
   * 包含了分组和聚合
   * @param sc 上下文
   */
  def rddReduceByKey(sc: SparkContext): Unit = {
    val dataRDD1 = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("a", 10), ("b", 20), ("c", 30)))
    val dataRDD2 = dataRDD1.reduceByKey(_ + _)
    val dataRDD3 = dataRDD1.reduceByKey(_ + _, 2)
    val result = dataRDD3.mapPartitionsWithIndex((index, iter) => {
      iter.map(x => s"Partition $index: $x")
    })

    // 打印结果
    result.collect().foreach(println)

    /**
     * 结果如下：
     * Partition 0: (b,22)
     * Partition 1: (a,11)
     * Partition 1: (c,33)
     */
  }

  /**
   * groupByKey： 将数据源的数据根据 key 对 value 进行分组
   * 只有分组功能
   * @param sc 上下文
   */
  def rddGroupByKey(sc: SparkContext): Unit = {
    val dataRDD1 = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3)))
    val dataRDD2 = dataRDD1.groupByKey()
    val dataRDD3 = dataRDD1.groupByKey(2)
    import org.apache.spark.HashPartitioner
    val dataRDD4 = dataRDD1.groupByKey(new HashPartitioner(2))
    val result = dataRDD4.mapPartitionsWithIndex((index, iter) => {
      iter.map(x => s"Partition $index: $x")
    })

    // 打印结果
    result.collect().foreach(println)

    /**
     * 结果如下：
     * Partition 0: (b,22)
     * Partition 1: (a,11)
     * Partition 1: (c,33)
     */
  }


  /**
   * def foldByKey(zeroValue: V)(func: (V, V) => V): RDD[(K, V)]
   * zeroValue：初始值，用于每个键的聚合。
   * func：用于值聚合的二元函数。
   *
   * foldByKey 是一个作用于 键值对类型 RDD 的聚合算子。它通过提供一个初始值和一个聚合函数，
   * 针对相同键的所有值进行归并操作。foldByKey 的特点是，它会在每个分区内先聚合数据，然后在分区间合并结果。这可以减少数据传输的开销。
   *
   * @param sc
   */
  def rddFoldByKey(sc: SparkContext): Unit = {
    // 创建键值对RDD
    val rdd = sc.parallelize(Seq(("a", 1), ("b", 2), ("a", 3), ("b", 4), ("a", 5), ("c", 6)))

    // 使用foldByKey求各键的累加值，并设置初始值为0
    val result = rdd.foldByKey(1)(_ + _)

    // 打印结果
    result.collect().foreach(println)

    /**
     * (a,9)
     * (b,6)
     * (c,6)
     */
  }

  /**
   * reduceByKey：不支持初始值，直接对键值对进行聚合。
   *
   * aggregateByKey：更灵活，允许指定分区内和分区间不同的聚合函数。
   *
   * foldByKey：更简洁，主要用于需要初始值的统一聚合场景。
   *
   * def aggregateByKey[U](zeroValue: U)(seqOp: (U, V) => U, combOp: (U, U) => U): RDD[(K, U)]
   * zeroValue：初始值，每个分区在开始时的聚合值。
   * seqOp：分区内的聚合函数，用于在每个分区内处理键对应的所有值。
   * combOp：分区间的合并函数，用于合并分区间聚合结果。
   *
   * @param sc
   */
  def rddAggregateByKey(sc: SparkContext): Unit = {
    val rdd = sc.parallelize(Seq(("a", 1), ("b", 2), ("a", 3), ("b", 4), ("a", 5), ("c", 6)) , 1)
    // 使用aggregateByKey
    val result = rdd.aggregateByKey(0)(
      (acc, value) => Math.max(acc, value), // 分区内取最大值
      (acc1, acc2) => acc1 + acc2           // 分区间累加最大值
    )

    // 打印结果
    result.collect().foreach(println)
  }

  /**
   *
   * combineByKey 是 Spark 中一个功能强大的键值对 RDD 算子，用于对相同键的数据进行高效聚合。
   * 与其他算子（如 reduceByKey 和 aggregateByKey）相比，combineByKey 提供了更大的灵活性。
   * 它允许我们通过自定义初始转换函数、分区内聚合函数和分区间合并函数来实现复杂的聚合逻辑
   *
   * def combineByKey[C](
   * createCombiner: V => C,
   * mergeValue: (C, V) => C,
   * mergeCombiners: (C, C) => C
   * ): RDD[(K, C)]
   *
   * createCombiner：分区内的初始转换函数，用于将每个键的第一个值转换为累加器。
   * mergeValue：分区内的聚合函数，用于将值与累加器组合。
   * mergeCombiners：分区间的合并函数，用于将不同分区的累加器合并。
   *
   * @param sc
   */
  def rddCombineByKey(sc: SparkContext): Unit = {
    // 创建键值对RDD
    val rdd = sc.parallelize(Seq(("a", 3), ("b", 7), ("a", 2), ("b", 6), ("a", 8), ("c", 5)))

    // 使用 combineByKey 计算每个键的平均值
    val result = rdd.combineByKey(
      (value: Int) => (value, 1),                // createCombiner: 初始化累加器为 (值, 计数)
      (acc: (Int, Int), value: Int) => (acc._1 + value, acc._2 + 1), // mergeValue: 累加值和计数
      (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2) // mergeCombiners: 合并分区结果
    ).mapValues{ case (sum, count) => sum.toDouble / count } // 计算平均值

    // 打印结果
    result.collect().foreach(println)
  }

  def rddSortByKey(sc: SparkContext): Unit = {
    val rdd = sc.parallelize(Seq(("a", 3), ("b", 7), ("a", 2), ("b", 6), ("a", 8), ("c", 5)))
    val result = rdd.sortByKey()
    // 打印结果
    result.collect().foreach(println)
  }

  /**
   *
   * join	内连接，返回两个 RDD 中键匹配的所有记录。
   * leftOuterJoin	左外连接，返回左侧 RDD 的所有记录及其匹配（如果没有匹配则为 None/空值）。
   * rightOuterJoin	右外连接，返回右侧 RDD 的所有记录及其匹配（如果没有匹配则为 None/空值）。
   * fullOuterJoin	全外连接，返回两个 RDD 中所有键的记录，如果没有匹配则为 None/空值。
   * @param sc
   */
  def rddJoin(sc: SparkContext): Unit = {
    // 创建两个键值对 RDD
    val rdd1 = sc.parallelize(Seq(("a", 1), ("b", 2), ("c", 3)))
    val rdd2 = sc.parallelize(Seq(("a", 4), ("b", 5), ("d", 6)))

    // 执行 join 操作
    val result = rdd1.join(rdd2)

    // 打印结果
    result.collect().foreach(println)

    /**
     * 结果如下：
     * (a,(1,4))
     * (b,(2,5))
     */

    // 执行 leftOuterJoin 操作
    val result2 = rdd1.leftOuterJoin(rdd2)

    // 打印结果
    result2.collect().foreach(println)
    /**
     * 结果如下：
     * (a,(1,Some(4)))
     * (b,(2,Some(5)))
     * (c,(3,None))
     */

    // 执行 rightOuterJoin 操作
    val result3 = rdd1.rightOuterJoin(rdd2)

    // 打印结果
    result3.collect().foreach(println)
    /**
     * 结果如下：
     * (a,(Some(1),4))
     * (b,(Some(2),5))
     * (d,(None,6))
     */

    // 执行 rightOuterJoin 操作
    val result4 = rdd1.fullOuterJoin(rdd2)

    // 打印结果
    result4.collect().foreach(println)
    /**
     * 结果如下：
     * (a,(Some(1),Some(4)))
     * (b,(Some(2),Some(5)))
     * (c,(Some(3),None))
     * (d,(None,Some(6)))
     */
  }


  /**
   * 在 Spark 中，cogroup 是一个强大的算子，用于联合处理多个键值对类型的 RDD，它会根据键将两个或多个 RDD 的值分组到一起。
   * cogroup 可以视为多个 RDD 的 groupByKey 操作，并支持复杂的键值关联。
   *
   * def cogroup[W](other: RDD[(K, W)]): RDD[(K, (Iterable[V], Iterable[W]))]
   * 将两个 RDD 按键分组，对应的键将存储在一个元组中：
   * 第一个 RDD 的值以 Iterable[V] 的形式存储。
   * 第二个 RDD 的值以 Iterable[W] 的形式存储。
   *
   * @param sc
   */
  def rddCogroup(sc: SparkContext): Unit = {
    // 创建两个键值对 RDD
    val rdd1 = sc.parallelize(Seq(("a", 1), ("b", 2), ("c", 3)))
    val rdd2 = sc.parallelize(Seq(("a", 4), ("b", 5), ("a", 6)))
    val rdd3 = sc.parallelize(Seq(("a", 23), ("b", 12), ("a", 26)))

    // 使用 cogroup 对两个 RDD 按键分组
    val result = rdd1.cogroup(rdd2).cogroup(rdd3)

    // 打印结果
    result.collect().foreach(println)

    /**
     * (a,(Seq((Seq(1),Seq(4, 6))),Seq(23, 26)))
     * (b,(Seq((Seq(2),Seq(5))),Seq(12)))
     * (c,(Seq((Seq(3),Seq())),Seq()))
     */
  }




  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("spark")
    val sparkContext: SparkContext = new SparkContext(sparkConf)
    // rddPartitionBy(sparkContext)
    // rddReduceByKey(sparkContext)
    // rddGroupByKey(sparkContext)
    // rddFoldByKey(sparkContext)
    // rddAggregateByKey(sparkContext)
    // rddSortByKey(sparkContext)
    // rddJoin(sparkContext)
    rddCogroup(sparkContext)
    sparkContext.stop()
  }
}
