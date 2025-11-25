package com.zishi.spark.core.demo01

import com.twitter.chill.KryoSerializer
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 采用 KryoSerializer 序列化框架
 */
object Rdd_Demo05 {

  def main(args: Array[String]): Unit = {
    //1.创建 SparkConf 并设置 App 名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")
      // 替换默认的序列化机制
      .set("spark.serializer", KryoSerializer.getClass.getName)
      // 注册需要使用 kryo 序列化的自定义类
      .registerKryoClasses(Array(classOf[Searcher]))
    //2.创建 SparkContext，该对象是提交 Spark App 的入口
    val sc: SparkContext = new SparkContext(conf)
    //3.创建一个 RDD
    val rdd: RDD[String] = sc.makeRDD(Array("hello world", "hello spark", "hive", "cinbuild"))
    //3.1 创建一个 Search 对象
    val searcher = new Searcher("hello")
    //3.2 函数传递，打印： ERROR Task not serializable
    searcher.getMatch1(rdd).collect().foreach(println)
    //3.3 属性传递，打印： ERROR Task not serializable
    searcher.getMatch2(rdd).collect().foreach(println)
    //4.关闭连接
    sc.stop()
  }

}

class Searcher(query: String) extends Serializable {
  def isMatch(s: String): Boolean = s.contains(query)
  // 函数序列化案例
  def getMatch1(rdd: RDD[String]): RDD[String] = rdd.filter(isMatch)
  // 属性序列化案例
  def getMatch2(rdd: RDD[String]): RDD[String] = rdd.filter(_.contains(query))
}


