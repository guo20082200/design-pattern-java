package com.zishi.spark.stream

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}
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

    //1. Create a local StreamingContext with two working thread and batch interval of 3 second.
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("StreamWordCount")
    //2. 初始化 SparkStreamingContext
    val ssc = new StreamingContext(sparkConf, Seconds(3))
    //3. Create a DStream that will connect to hostname:port, like localhost:9999
    val lineStreams = ssc.socketTextStream("localhost", 9999)
    // 4. Split each line into words
    val wordStreams = lineStreams.flatMap(_.split(" "))
    //5. Count each word in each batch
    val wordAndOneStreams = wordStreams.map((_, 1))
    //  将相同的单词次数做统计
    val wordAndCountStreams = wordAndOneStreams.reduceByKey(_+_)
    //6. Print the first ten elements of each RDD generated in this DStream to the console
    wordAndCountStreams.print()
    //7. 启动 SparkStreamingContext
    ssc.start()
    //8. Wait for the computation to terminate
    ssc.awaitTermination()
  }
}
