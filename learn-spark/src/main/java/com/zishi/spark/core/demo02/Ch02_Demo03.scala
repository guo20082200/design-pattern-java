package com.zishi.spark.core.demo02

import org.apache.spark.sql.SparkSession

object Ch02_Demo03 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("SparkDemo")
      .master("local[4]")
      .getOrCreate()

    /**
     * combineByKey： 底层的实现都是 combineByKeyWithClassTag
     * def combineByKey[C](
     * createCombiner: V => C,
     * mergeValue: (C, V) => C,
     * mergeCombiners: (C, C) => C,
     * partitioner: Partitioner,
     * mapSideCombine: Boolean = true,
     * serializer: Serializer = null): RDD[(K, C)] = self.withScope {
     * combineByKeyWithClassTag(createCombiner, mergeValue, mergeCombiners,
     * partitioner, mapSideCombine, serializer)(null)
     * }
     * combineByKeyWithClassTag 得简单实现，按照哈希分区，输出rdd
     *
     *
     * createCombiner: V => C ，这个函数把当前的值作为参数，此时我们可以对其做些附加操作(类型转换)并把它返回 (这一步类似于初始化操作)
     * mergeValue: (C, V) => C，该函数把元素V合并到之前的元素C(createCombiner)上 (这个操作在每个分区内进行)
     * mergeCombiners: (C, C) => C，该函数把2个元素C合并 (这个操作在不同分区间进行)
     *
     *
     */
    println("........................combineByKey")
    val initialScores = Array(("Fred", 88.0), ("Fred", 95.0), ("Fred", 91.0), ("Wilma", 93.0), ("Wilma", 95.0), ("Wilma", 98.0))
    val d1 = spark.sparkContext.parallelize(initialScores)
    type MVType = (Int, Double) //定义一个元组类型(科目计数器,分数)


    def createCombiner(score: Double): MVType = {
      println(score)
      (1, score)
    }

    def mergeValue(c1: MVType, newScore: Double): MVType = {
      println("mergeValue:" + c1  +":   " + newScore)
      (c1._1 + 1, c1._2 + newScore)
    }

    def mergeCombiners(c1: MVType, c2: MVType):MVType = {
      println("mergeCombiners:" + c1  +":   " + c2)
      (c1._1 + c2._1, c1._2 + c2._2)
    }

    val value = d1.combineByKey(createCombiner, mergeValue, mergeCombiners)
    println(value.collect().mkString("Array(", ", ", ")"))

    //.map { case (name, (num, score)) => (name, score / num) }.collect

    val collect = d1.combineByKey(
      score => (1, score),
      (c1: MVType, newScore) => (c1._1 + 1, c1._2 + newScore),
      (c1: MVType, c2: MVType) => (c1._1 + c2._1, c1._2 + c2._2)
    ).map { case (name, (num, score)) => (name, score / num) }.collect
    println(collect.mkString)


    val rdd = spark.sparkContext.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6),
    ), 2)

    rdd.combineByKey(
      v => (v, 1),
      (t: (Int, Int), v) => {
        (t._1 + v, t._2 + 1)
      },
      (t1: (Int, Int), t2: (Int, Int)) => {
        (t1._1 + t2._1, t1._2 + t2._2)
      }
    ).collect().foreach(println)
  }

}

