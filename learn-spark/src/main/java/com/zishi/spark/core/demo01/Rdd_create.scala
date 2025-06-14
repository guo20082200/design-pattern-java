package com.zishi.spark.core.demo01

import org.apache.spark.{SparkConf, SparkContext}

object Rdd_create {


  //1.  从内存/集合创建RDD
  def rddCreate01(sc: SparkContext): Unit = {
    val seq = Seq[Int](1, 2, 3, 4, 5)
    val rdd = sc.makeRDD(seq, 3)

    println(rdd.getNumPartitions)
    rdd.collect().foreach(println(_))
  }

  //2.  从文件/文件夹创建RDD
  // 文件路径可以使用通配符
  def rddCreate02(sc: SparkContext): Unit = {
    //val filePath = "adatas/test_WordCount.txt"
    val filePath = "adatas"
    val rdd = sc.textFile(filePath)
    rdd.collect().foreach(println(_))
  }

  //3.  从rdd创建RDD
  def rddCreate03(sc: SparkContext): Unit = {

  }

  //4.  从rdd创建RDD
  def rddCreate04(sc: SparkContext): Unit = {
    /**
     * makeRDD 第二个参数为 分区的个数，可以不传，有个默认值：defaultParallelism（默认并行度）
     *
     * scheduler.conf.getInt("spark.default.parallelism", totalCores)
     * spark在默认情况下，从配置对象中获取配置参数，spark.default.parallelism
     * 如果获取不到，那么使用totalCores属性，这个是当前运行环境的最大可用核数
     *
     */
    val rdd = sc.makeRDD(List(12, 2, 4, 5, 6))

    println(rdd.getNumPartitions)
    rdd.collect().foreach(println(_))
  }

  //5. 分区数的设置
  def rddCreate05(sc: SparkContext) :Unit = {

    //RDD的并行度 & 分区
    //makeRDD方法可以传递第二个参数，这个参数表示分区的数量
    //第二个参数可以不传递的，那么makeRDD方法会使用默认值：defaultParallelism（默认并行度）
    //  scheduler.conf.getInt("spark.default.parallelism", totalCores)
    //  spark在默认情况下，从配置对象中获取配置参数：spark.default.parallelism
    //  如果获取不到，那么使用totalCores属性，这个属性取值为当前运行环境的最大可用核数

    //【1，2】，【3，4】
    //val rdd = sc.makeRDD(List(1,2,3,4), 2)

    //【1】，【2】，【3，4】
    //val rdd = sc.makeRDD(List(1,2,3,4), 3)

    //【1】，【2,3】，【4,5】
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5), 3)
    //将处理的数据保存成分区文件
    rdd.saveAsTextFile("output")
  }


  //6. 读取文件的分区数设置
  private def rddCreate06(sc: SparkContext) :Unit = {
    //  TODO 数据分区的分配
    //1. 数据以行为单位进行读取
    //    spark读取文件，采用的是hadoop的方式读取，所以一行一行读取，和字节数没有关系
    //2. 数据读取时以偏移量为单位，偏移量不会被重复读取
    /*
       偏移量
       1@@  => 012
       2@@  => 345
       3    => 6
     */
    //3. 数据分区的偏移量范围的计算
    // 0 => [0, 3]  => 12
    // 1 => [3, 6]  => 3
    // 2 => [6, 7]  =>
    val rdd = sc.textFile("adatas/2.txt", 2)
    rdd.saveAsTextFile("output")
  }



  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("word_count").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    //rddCreate01(sc)
    //rddCreate02(sc)
    //rddCreate03(sc)
    //rddCreate04(sc)
    //rddCreate05(sc)
    rddCreate06(sc)

    sc.stop()

  }
}
