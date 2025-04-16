package com.zishi.sp.core

import org.apache.spark._
import org.apache.spark.util.{AccumulatorV2, LongAccumulator}


/**
 * RDD的累加器:
 * 累加器（Accumulator） 是一种共享变量，主要用于执行全局累加操作。它们是并行计算中非常实用的工具，特别适合进行 计数、求和 等操作，同时避免多线程环境中的数据竞争问题。
 *
 * 1. 累加器的特点
 * 仅支持累加操作：
 * 累加器只能通过累加的方式更新值，不能执行减法或其他复杂操作。
 * 工作节点只能对累加器的值进行更新，不能读取其当前值。
 *
 * 驱动程序读取：
 * 累加器的值只能在 Driver（驱动程序） 中读取。
 *
 * 适用场景：
 * 数据统计，例如记录处理的数据总量或操作次数。
 * 日志分析，统计特定日志信息的数量。
 *
 *
 * 2. 累加器的类型
 * Spark 提供了两种累加器：
 * 数值累加器：用于累加数值类型的数据，例如整数或浮点数。
 * 自定义累加器：用户可以定义自己的累加器类型，用于复杂的场景。
 */
object Rdd_Demo10 {


  def rddAccumulator(sc: SparkContext): Unit = {
    // 创建一个累加器
    val accumulator: LongAccumulator = sc.longAccumulator("My Accumulator")

    // 创建 RDD 并对数据进行累加
    val rdd = sc.parallelize(Seq(1, 2, 3, 4, 5))

    rdd.foreach(x => accumulator.add(x))
    // 打印累加器的值
    println(s"Accumulator value: ${accumulator.value}")
  }

  def rddAccumulator02(sc: SparkContext): Unit = {
    // 创建一个累加器
    val stringAccumulator: StringAccumulator = new StringAccumulator()
    sc.register(stringAccumulator, "StringAccumulator")

    // 创建 RDD 并对数据进行累加
    val rdd = sc.parallelize(Seq("Hello", " ", "World"))
    rdd.foreach(word => stringAccumulator.add(word))
    // 打印累加器的值
    println(s"String Accumulator value: ${stringAccumulator.value}")
  }

  def rddBroadcast(sc: SparkContext) :Unit = {

    // 创建一个广播变量
    val broadcastVar = sc.broadcast(Seq("Alice", "Bob", "Charlie"))
    // 使用广播变量
    val rdd = sc.parallelize(Seq("Alice", "David", "Charlie", "Eve"))
    val filteredRDD = rdd.filter(name => broadcastVar.value.contains(name))
    // 打印结果
    filteredRDD.collect().foreach(println)
  }

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Hello")
    val sc: SparkContext = new SparkContext(sparkConf)
    //rddAccumulator(sc)
    //rddAccumulator02(sc)
    rddBroadcast(sc)
    sc.stop()
  }
}


// 自定义字符串累加器
class StringAccumulator extends AccumulatorV2[String, String] {
  private var result = ""

  override def isZero: Boolean = result.isEmpty

  override def copy(): AccumulatorV2[String, String] = {
    val newAcc = new StringAccumulator()
    newAcc.result = this.result
    newAcc
  }

  override def reset(): Unit = result = ""

  override def add(v: String): Unit = result += v

  override def merge(other: AccumulatorV2[String, String]): Unit = {
    result += other.value
  }

  override def value: String = result
}