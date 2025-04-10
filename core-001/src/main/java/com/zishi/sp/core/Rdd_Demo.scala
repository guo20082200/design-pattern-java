package com.zishi.sp.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * rdd创建
 */
object Rdd_Demo {


  /**
   * 从内存创建
   * makeRDD 方法其实就是parallelize 方法
   */
  def rddCreate01(sparkContext: SparkContext): Unit = {
    //val rdd1 = sparkContext.parallelize(List(1, 2, 3, 4))
    val rdd2 = sparkContext.makeRDD(List(1, 2, 3, 4))
    rdd2.collect().foreach(println)
  }


  /**
   * 从外部存储（文件） 创建RDD，
   * HDFS、 HBase
   */
  def rddCreate02(sparkContext: SparkContext): Unit = {
    val fileRDD: RDD[String] = sparkContext.textFile("adatas/test_WordCount.txt")
    fileRDD.collect().foreach(println)
    sparkContext.stop()
  }

  /**
   * RDD 并行度与分区
   */
  def rddCreate03(sparkContext: SparkContext): Unit = {
    val dataRDD: RDD[Int] =
      sparkContext.makeRDD(List(1, 2, 3, 4), 4)
    dataRDD.collect().foreach(println)

    val fileRDD: RDD[String] = sparkContext.textFile("adatas/test_WordCount.txt", 2)
    fileRDD.collect().foreach(println)
  }

  /**
   * 将处理的数据逐条进行映射转换，这里的转换可以是类型的转换，也可以是值的转换
   *
   * @param sc
   */
  def rddMap(sc: SparkContext): Unit = {
    // 创建RDD
    val rdd = sc.parallelize(Seq(1, 2, 3, 4, 5))

    // 使用map算子将每个元素乘以2
    val result = rdd.map(x => x * 2)

    // 打印结果
    result.collect().foreach(println)
  }

  /**
   * mapPartitions 对RDD的每个分区进行操作，分区内的数据由迭代器传递。它更适合需要操作整个分区的数据场景，例如连接数据库或批量处理。
   *
   * 两者的区别：
   * 操作粒度：
   * 1. map是对单个元素进行操作。
   * 2. mapPartitions是对分区中的所有元素作为一个整体进行操作。
   *
   * 性能优化：
   * 1. map会频繁创建对象，处理数据时开销较大。
   * 2. mapPartitions对整个分区进行批处理，可以减少开销，提高性能。
   *
   * 适用场景：
   * 1. 如果需要简单地对每个元素执行操作，可以使用map。
   * 2. 如果需要基于分区进行复杂操作或与外部资源交互（如数据库连接），建议使用mapPartitions。
   */
  def rddMapPartitions(sc: SparkContext): Unit = {
    // 创建RDD
    val rdd = sc.parallelize(Seq(1, 2, 3, 4, 5), numSlices = 2) // 创建两个分区

    // 使用mapPartitions算子
    val result = rdd.mapPartitions(iter => {
      iter.map(x => x * 2)
    })
    // 打印结果
    result.collect().foreach(println)
  }

  /**
   * mapPartitionsWithIndex 是 Spark 中的一个强大的算子，
   * 它允许我们在处理数据时获取分区索引并针对每个分区的数据进行操作
   *
   * @param sc
   */
  def rddMapPartitionsWithIndex(sc: SparkContext): Unit = {
    // 创建RDD
    val rdd = sc.parallelize(Seq(1, 2, 3, 4, 5, 6, 7, 8), numSlices = 3) // 创建3个分区

    // 使用 mapPartitionsWithIndex 算子
    val result = rdd.mapPartitionsWithIndex((index, iter) => {
      // 对每个分区的数据进行操作，同时可以使用分区索引
      iter.map(x => s"Partition $index: $x + 2")
    })

    // 打印结果
    result.collect().foreach(println)
  }

  /**
   * map 输出的是一个集合（每个元素保持独立）。
   *
   * flatMap 输出是扁平化后的多个元素。
   *
   * @param sc
   */
  def rddFlatMap(sc: SparkContext): Unit = {
    // 创建RDD
    val rdd = sc.parallelize(Seq("apple orange", "banana mango", "grape"))

    // 使用flatMap将每个字符串拆分为单独的单词
    val result = rdd.flatMap(line => line.split(" "))
    // 打印结果
    result.collect().foreach(println)
  }

  /**
   * glom函数
   * 将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变
   *
   * @param sc
   */
  def rddGlom(sc: SparkContext): Unit = {
    val dataRDD = sc.makeRDD(List(1, 2, 3, 4), 3)
    val dataRDD1: RDD[Array[Int]] = dataRDD.glom()

    dataRDD1.collect().foreach(x => println(x.mkString("Array(", ", ", ")")))

    /**
     * 输出结果如下：
     * Array(1)
     * Array(2)
     * Array(3, 4)
     */
  }

  /**
   * 小功能：计算所有分区最大值求和（分区内取最大值，分区间最大值求和）
   *
   * @param sc
   */
  def rddEx01(sc: SparkContext): Unit = {
    val dataRDD = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), 3)
    val rdd: RDD[Array[Int]] = dataRDD.glom()

    rdd.collect().foreach(x => println(x.mkString("Array(", ", ", ")")))
    println(".................")
    val res = rdd.map(x => x.max).sum()
    print(res)
  }

  /**
   * 将数据根据指定的规则进行分组, 分区默认不变，但是数据会被打乱重新组合，我们将这样
   * 的操作称之为shuffle。极限情况下，数据可能被分在同一个分区中
   * 一个组的数据在一个分区中，但是并不是说一个分区中只有一个组
   *
   * @param sc
   */
  def rddGroupBy(sc: SparkContext): Unit = {
    val dataRDD = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), 3)

    // RDD[(K, Iterable[T])]
    val grdd = dataRDD.groupBy(x => x % 2)
    //println(grdd.getNumPartitions) // 3
    // println(grdd.partitions.mkString("Array(", ", ", ")")) // Array(org.apache.spark.rdd.ShuffledRDDPartition@0, org.apache.spark.rdd.ShuffledRDDPartition@1, org.apache.spark.rdd.ShuffledRDDPartition@2)


    grdd.foreach(it => {
      println(it._1)
      print(it._2)
    })

    val result = grdd.mapPartitionsWithIndex((index, iter) => {
      iter.map(x => s"Partition $index: $x")
    })

    // 打印结果
    result.collect().foreach(println)
  }

  /**
   * 小功能：将 List("Hello", "hive", "hbase", "Hadoop")根据单词首写字母进行分组
   *
   * @param sc
   */
  def rddEx02(sc: SparkContext): Unit = {
    val dataRDD = sc.makeRDD(List("Hello", "hive", "hbase", "Hadoop"))

    val grdd = dataRDD.groupBy(x => x.substring(0, 1))
    grdd.foreach(it => {
      println(it._1)
      print(it._2)
    })

    // Seq(Hello, Hadoop) Seq(hive, hbase)
  }

  /**
   * 将数据根据指定的规则进行筛选过滤，符合规则的数据保留，不符合规则的数据丢弃。
   * 当数据进行筛选过滤后，分区不变，但是分区内的数据可能不均衡，生产环境下，可能会出现数据倾斜。
   *
   * @param sc spark上下文
   */
  def rddFilter(sc: SparkContext): Unit = {
    val dataRDD = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), 3)

    // val grdd = dataRDD.filter(x => x % 2 == 1)
    val grdd = dataRDD.filter(_ % 3 == 1)


    val result = grdd.mapPartitionsWithIndex((index, iter) => {
      iter.map(x => s"Partition $index: $x")
    })

    // 打印结果
    result.collect().foreach(println)

    /**
     * 输出结果如下：存在数据倾斜
     *
     * Partition 0: 1
     * Partition 1: 4
     * Partition 1: 7
     * Partition 2: 10
     */
  }

  /**
   * 从rdd数据集中采样
   *
   * @param sc
   */
  def rddSample(sc: SparkContext): Unit = {
    val dataRDD = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), 3)

    /**
     * 抽取数据不放回（伯努利采样）
     * 伯努利算法：又叫 0、 1 分布。 例如扔硬币，要么正面，要么反面。
     * 具体实现：根据种子和随机算法算出一个数和第二个参数设置几率比较，小于第二个参数要，大于不 要
     * 第一个参数：抽取的数据是否放回， false：不放回
     * 第二个参数：抽取的几率，范围在[0,1]之间,0：全不取； 1：全取；
     * 第三个参数：随机数种子
     */
    val result = dataRDD.sample(withReplacement = false, 0.5)

    // 打印结果
    result.collect().foreach(println)


    println(".............................")

    /**
     * 抽取数据放回（泊松算法）
     * 第一个参数：抽取的数据是否放回， true：放回； false：不放回
     * 第二个参数：重复数据的几率，范围大于等于 0.表示每一个元素被期望抽取到的次数
     * 第三个参数：随机数种子
     */
    val result2 = dataRDD.sample(withReplacement = true, 0.5)

    // 打印结果
    result2.collect().foreach(println)

  }


  /**
   * 去重
   *
   * @param sc
   */
  def rddDistinct(sc: SparkContext): Unit = {
    val dataRDD = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2, 3, 4), 3)

    val res = dataRDD.mapPartitionsWithIndex((index, iter) => {
      iter.map(x => s"Partition $index: $x")
    })

    // 打印结果
    res.collect().foreach(println)

    /**
     * 输出结果如下：
     * Partition 0: 1
     * Partition 0: 2
     * Partition 0: 3
     * Partition 0: 4
     * Partition 0: 5
     * Partition 1: 6
     * Partition 1: 7
     * Partition 1: 8
     * Partition 1: 9
     * Partition 1: 10
     * Partition 2: 11
     * Partition 2: 1
     * Partition 2: 2
     * Partition 2: 3
     * Partition 2: 4
     */
    println("......................")

    val rdd = dataRDD.distinct()

    val result = rdd.mapPartitionsWithIndex((index, iter) => {
      iter.map(x => s"Partition $index: $x")
    })

    // 打印结果
    result.collect().foreach(println)

    /**
     * 输出结果如下：
     * Partition 0: 6
     * Partition 0: 3
     * Partition 0: 9
     * Partition 1: 4
     * Partition 1: 1
     * Partition 1: 7
     * Partition 1: 10
     * Partition 2: 11
     * Partition 2: 8
     * Partition 2: 5
     * Partition 2: 2
     */
  }

  /**
   * coalesce：coalesce 是一个常用的RDD操作，用于减少分区数量，从而优化资源使用
   *
   * @param sc
   */
  def rddCoalesce(sc: SparkContext): Unit = {
    val dataRDD = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2, 3, 4), 10)

    /**
     * numPartitions: 分区数
     * shuffle： 是否进行shuffle， 默认false
     *
     * 不启用shuffle时，数据不会重新分布，因此性能较高，但分区数据可能不均。
     *
     * 启用shuffle时，性能会有所降低，但可以确保分区数据更加均衡。
     */
    val rdd = dataRDD.coalesce(2)
    val result = rdd.mapPartitionsWithIndex((index, iter) => {
      iter.map(x => s"Partition $index: $x")
    })

    // 打印结果
    result.collect().foreach(println)
  }

  /**
   * `repartition` 和 `coalesce` 都是 Spark 中的分区操作，用于调整 RDD 或 DataFrame 的分区数量，但它们的实现方式和适用场景有所不同。以下是详细对比：
   *
   * | **特性**             | **repartition**                                   | **coalesce**                                   |
   * |----------------------|--------------------------------------------------|----------------------------------------------|
   * | **分区调整方向**       | 可用于增加和减少分区数量                        | 主要用于减少分区数量                          |
   * | **是否触发 Shuffle**   | 始终触发 Shuffle 操作，重新分布数据，确保分区均匀 | 可以选择是否触发 Shuffle（默认为不触发）       |
   * | **性能开销**         | 因为触发 Shuffle，开销较大                      | 不触发 Shuffle 时性能较高，开销较小            |
   * | **数据均匀性**        | 数据在目标分区中重新均匀分布                   | 数据在减少后的分区中可能不均匀，适合减少分区时 |
   * | **典型场景**         | 增加分区以提升并行度，或在分区数据不均时重新平衡 | 减少分区数量以优化资源利用，适合减少分区但不关注数据均匀性 |
   *
   * ---
   *
   * ### **适用场景总结**
   * - **`repartition`**：适用于需要调整分区数量（增加或减少）且需要数据在新分区中均匀分布的场景，例如提高并行度或处理数据倾斜问题。
   * - **`coalesce`**：适用于减少分区数量以减少调度开销的场景，尤其是对数据均匀性要求不高的情况。
   *
   * @param args
   */
  def rddRepartition(sc: SparkContext): Unit = {
    val dataRDD = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2, 3, 4), 3)

    val rdd = dataRDD.repartition(2)
    val result = rdd.mapPartitionsWithIndex((index, iter) => {
      iter.map(x => s"Partition $index: $x")
    })

    // 打印结果
    result.collect().foreach(println)
  }

  /**
   * 对源RDD 和参数RDD 求交集后返回一个新的RDD
   *
   * @param sc
   */
  def rddIntersection(sc: SparkContext): Unit = {
    val dataRDD1 = sc.makeRDD(List(1, 2, 3, 4))
    val dataRDD2 = sc.makeRDD(List(3, 4, 5, 6))
    //val dataRDD2 = sc.makeRDD(List("a", "b"))
    val dataRDD = dataRDD1.intersection(dataRDD2)

    // 打印结果
    dataRDD.collect().foreach(println)
  }

  /**
   * union： 对源RDD 和参数RDD 求并集后返回一个新的RDD，不去重
   *
   * @param sc
   */
  def rddUnion(sc: SparkContext): Unit = {
    val dataRDD1 = sc.makeRDD(List(1, 2, 3, 4))
    val dataRDD2 = sc.makeRDD(List(3, 4, 5, 6))
    //val dataRDD2 = sc.makeRDD(List("a", "b"))
    val dataRDD = dataRDD1.union(dataRDD2)

    // 打印结果
    dataRDD.collect().foreach(println)
  }


  /**
   * subtract：以一个 RDD 元素为主，去除两个 RDD 中重复元素，将其他元素保留下来。求差集
   *
   * @param sc
   */
  def rddSubtract(sc: SparkContext): Unit = {
    val dataRDD1 = sc.makeRDD(List(1, 2, 3, 4))
    val dataRDD2 = sc.makeRDD(List(3, 4, 5, 6))
    val dataRDD = dataRDD1.subtract(dataRDD2)
    // 打印结果
    dataRDD.collect().foreach(println)
  }


  /**
   * zip:
   * 将两个 RDD 中的元素，以键值对的形式进行合并。其中，键值对中的Key 为第 1 个 RDD
   * 中的元素， Value 为第 2 个 RDD 中的相同位置的元素
   *
   * @param args
   */
  def rddZip(sc: SparkContext): Unit = {
    val dataRDD1 = sc.makeRDD(List(1, 2, 3, 4))
    val dataRDD2 = sc.makeRDD(List(3, 4, 5, 6))
    val dataRDD = dataRDD1.zip(dataRDD2)
    // 打印结果
    dataRDD.collect().foreach(println)

    //如果两个RDD 数据类型不一致怎么办？
    val dataRDD3 = sc.makeRDD(List("a", "b", "c", "d"))
    val dataRDD02 = dataRDD1.zip(dataRDD3)
    // 打印结果
    dataRDD02.collect().foreach(println)


  }

  /**
   * 如果两个RDD 数据分区不一致怎么办？
   * 报错：  Can't zip RDDs with unequal numbers of partitions: List(3, 4)
   * @param sc
   */
  def rddZip02(sc: SparkContext): Unit = {
    val dataRDD1 = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2, 3, 4), 3)
    val dataRDD2 = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2, 3, 4), 4)
    val dataRDD = dataRDD1.zip(dataRDD2)
    // 打印结果
    dataRDD.collect().foreach(println)
  }

  /**
   * 如果两个RDD 分区数据数量不一致怎么办？
   * 报错： Can only zip RDDs with same number of elements in each partition
   * @param args
   */
  def rddZip03(sc: SparkContext): Unit = {
    val dataRDD1 = sc.makeRDD(List(1, 2, 3, 3), 1)
    val dataRDD2 = sc.makeRDD(List(1, 2), 1)
    val dataRDD = dataRDD1.zip(dataRDD2)
    // 打印结果
    dataRDD.collect().foreach(println)
  }


  def main(args: Array[String]): Unit = {

    val sparkConf =
      new SparkConf().setMaster("local[*]").setAppName("spark")
    val sparkContext = new SparkContext(sparkConf)

    // rddCreate01(sparkContext)
    // rddCreate02(sparkContext)
    // rddCreate03(sparkContext)
    // rddMap(sparkContext)
    // rddMapPartitionsWithIndex(sparkContext)
    // rddFlatMap(sparkContext)
    // rddGlom(sparkContext)
    // rddEx01(sparkContext)
    // rddGroupBy(sparkContext)
    // rddEx02(sparkContext)
    // rddFilter(sparkContext)
    // rddSample(sparkContext)
    // rddDistinct(sparkContext)
    // rddCoalesce(sparkContext)
    // rddRepartition(sparkContext)
    // rddIntersection(sparkContext)
    // rddUnion(sparkContext)
    // rddSubtract(sparkContext)
    // rddZip(sparkContext)
    // rddZip02(sparkContext)
    rddZip03(sparkContext)

    sparkContext.stop()
  }
}
