package com.zishi.sp.core

import com.twitter.chill.KryoSerializer
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * rdd的血缘关系和依赖关系
 */
object Rdd_Demo06 {


  /**
   * RDD 血缘关系
   * @param sc
   */
  def rddLineage(sc: SparkContext): Unit = {
    val fileRDD: RDD[String] = sc.textFile("adatas/test_WordCount.txt")
    println(fileRDD.toDebugString)
    println(" ")
    val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))
    println(wordRDD.toDebugString)
    println(" ")
    val mapRDD: RDD[(String, Int)] = wordRDD.map((_, 1))
    println(mapRDD.toDebugString)
    println(" ")
    val resultRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    println(resultRDD.toDebugString)
    resultRDD.collect()
  }

  /**
   * RDD依赖关系
   *
   * 这里所谓的依赖关系，其实就是两个相邻 RDD 之间的关系
   * @param sc
   */
  def rddDependencies(sc: SparkContext): Unit = {
    val fileRDD: RDD[String] = sc.textFile("adatas/test_WordCount.txt")
    println(fileRDD.dependencies)
    println(" ")
    val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))
    println(wordRDD.dependencies)
    println(" ")
    val mapRDD: RDD[(String, Int)] = wordRDD.map((_,1))
    println(mapRDD.dependencies)
    println(" ")
    val resultRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_+_)
    println(resultRDD.dependencies)
    resultRDD.collect()
  }


  def main(args: Array[String]): Unit = {

    //1.创建 SparkConf 并设置 App 名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")
      // 替换默认的序列化机制
      .set("spark.serializer", KryoSerializer.getClass.getName)
      // 注册需要使用 kryo 序列化的自定义类
      .registerKryoClasses(Array(classOf[Searcher]))
    //2.创建 SparkContext，该对象是提交 Spark App 的入口
    val sc: SparkContext = new SparkContext(conf)

    //rddLineage(sc)
    rddDependencies(sc)

    sc.stop()
  }
}
