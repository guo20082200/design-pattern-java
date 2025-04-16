package com.zishi.spark.stream

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object Stream_Demo01 {

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




  def main(args: Array[String]): Unit = {

    //1.初始化 Spark 配置信息
    val sparkConf = new
        SparkConf().setMaster("local[*]").setAppName("StreamWordCount")
    //2.初始化 SparkStreamingContext
    val ssc = new StreamingContext(sparkConf, Seconds(3))
    //3.通过监控端口创建 DStream，读进来的数据为一行行
    val lineStreams = ssc.socketTextStream("linux1", 9999)
    //将每一行数据做切分，形成一个个单词
    val wordStreams = lineStreams.flatMap(_.split(" "))
    //将单词映射成元组（word,1）
    val wordAndOneStreams = wordStreams.map((_, 1))
    //将相同的单词次数做统计
    val wordAndCountStreams = wordAndOneStreams.reduceByKey(_+_)
    //打印
    wordAndCountStreams.print()
    //启动 SparkStreamingContext
    ssc.start()
    ssc.awaitTermination()
  }
}
