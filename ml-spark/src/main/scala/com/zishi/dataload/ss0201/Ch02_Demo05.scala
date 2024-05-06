package com.zishi.dataload.ss0201

import org.apache.spark.sql.SparkSession

object Ch02_Demo05 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("SparkDemo")
      .master("local[4]")
      .getOrCreate()

    println("........................zip, zipWithIndex, zipPartitions ")

    val sc = spark.sparkContext
    val a = Array(1, 2, 3, 3)
    val b = Array("a", "b", "a", "c")
    val da = sc.parallelize(a)
    val db = sc.parallelize(b)

    /**
     * zip: 要求两个rdd元素的个数一样
     * Can only zip RDDs with same number of elements in each partition
     */
    println(da.zip(db).collect().mkString) // (1,a)(2,b)(3,a)(3,c)

    val value = db.zipWithIndex()
    println(value.collect().mkString("Array(", ", ", ")")) // Array((a,0), (b,1), (a,2), (c,3))

    val value1 = db.zipWithUniqueId()
    println(value1.collect().mkString("Array(", ", ", ")")) // Array((a,0), (b,1), (a,2), (c,3))


    val rddData1 = sc.parallelize(1 to 10, 2)
    val rddData2 = sc.parallelize(20 to 25, 2)

    /**
     * (f: (Iterator[T], Iterator[B]) => Iterator[V]): RDD[V]
     * 第一个参数传入另一个RDD。
     * 第二个参数传入函数（用于定义如何对每一个分区中的元素进行zip操作）
     *
     *
     */
    def func(rddIter1: Iterator[Int], rddIter2: Iterator[Int]): Iterator[(Int, Int)] = {
      var result = List[(Int, Int)]()
      while (rddIter1.hasNext && rddIter2.hasNext) {
        result ::= (rddIter1.next(), rddIter2.next())
      }
      result.iterator
    }

    // 第一个分区三个元素，第二个分区5个元素
    //val value2 = rddData1.zipPartitions(rddData2)(func)
    val value2 = rddData1.zipPartitions(rddData2) {
      (t1, t2) => {
        var result = List[(Int, Int)]()
        while (t1.hasNext && t2.hasNext) {
          result ::= (t1.next(), t2.next())
        }
        result.iterator
      }
    }
    println("..............." + value2.collect().mkString("Array(", ", ", ")")) // ...............Array((3,22), (2,21), (1,20), (8,25), (7,24), (6,23))


    println("........................coalesce ")
    /**
     * coalesce: 将rdd进行重新分区，而不进行shuffle
     */
    val value3 = rddData1.coalesce(4)

    println("........................coalesce ")
    /**
     * repartition： 将rdd进行重新分区，并且进行shuffle
     */
    val value4 = rddData1.repartition(4)
  }

}

