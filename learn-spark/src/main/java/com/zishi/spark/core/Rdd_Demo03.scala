package com.zishi.sp.core


import org.apache.spark.{SparkConf, SparkContext}

object Rdd_Demo03 {

  def rddAction01(sc: SparkContext):Unit = {
    val rdd = sc.parallelize(Seq(1, 20, 3, 4, 5))
    val result = rdd.collect()
    println(result.mkString(", "))


    val count = rdd.count() // 统计RDD中元素的数量。
    println(count)

    val first = rdd.first() // 返回RDD中的第一个元素。
    println(first)

    val take = rdd.take(3) // 返回RDD中前`n`个元素。
    println(take)

    val takeOrdered = rdd.takeOrdered(3) // 返回RDD中排序后的前`n`个元素。
    println(takeOrdered)

    val top = rdd.top(3) // 返回RDD中最大的`n`个元素。
    println(top)

    val reduce = rdd.reduce(_ + _) // 通过指定的二元操作（如加法、乘法）聚合RDD中的元素。
    println(reduce)

    val fold = rdd.fold(10)(_ + _) // 在`reduce`的基础上增加一个初始值。
    println(fold) // 该结果和rdd的分区数有关。

    // aggregate： 更灵活的聚合操作，允许不同分区有不同的初始值。
    val aggregate = rdd.aggregate(0)((acc, value) => acc + value, _ + _)
    println(aggregate)

    //
    rdd.foreach(println)  //对RDD中的每个元素执行指定的操作（通常用于打印或写出数据）。


    val rdd2 = sc.parallelize(Seq(1, 2, 2, 3, 3, 3))
    val countByValue = rdd2.countByValue() //统计RDD中每个值出现的次数。
    println(countByValue) // Map(1 -> 1, 2 -> 2, 3 -> 3)



  }

  def rddAction02(sc: SparkContext):Unit = {
    val rdd3 = sc.parallelize(Seq("spark", "hadoop", "big data"), 1)
    //rdd3.saveAsTextFile("output_path") // 为什么出来了12个文件？默认写出来是12个文件么？还是和rdd的分区数有关？

    rdd3.saveAsObjectFile("output_path") //将RDD以对象文件格式保存。

  }

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("spark")
    val sparkContext: SparkContext = new SparkContext(sparkConf)
    //rddAction01(sparkContext)
    rddAction02(sparkContext)
    sparkContext.stop()
  }

}
