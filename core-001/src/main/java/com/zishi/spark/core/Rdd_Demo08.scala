package com.zishi.sp.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * RDD的持久化
 */
object Rdd_Demo08 {


  /**
   * 没有缓存的情况下，每次都需要重新计算
   * @param sc
   */
  def wordCountNoCache(sc: SparkContext): Unit = {

    // 创建RDD
    val listRDD = sc.makeRDD(List("welcome to hangge.com", "hello world", "hangge.com"))

    // 对RDD进行转换操作
    val wordRDD: RDD[String] = listRDD.flatMap(_.split(" "))
    val mapRDD: RDD[(String, Int)] = wordRDD.map {
      word => {
        println("*** wordRDD.map ***")
        (word, 1)
      }
    }

    // 统计单词数量
    val reduceRDD = mapRDD.reduceByKey(_+_)
    reduceRDD.collect().foreach(println)
    println("-------------------------")

    // 单词分组
    val groupRDD = mapRDD.groupByKey()
    groupRDD.collect().foreach(println)

    //关闭 Spark
    sc.stop()
  }

  /**
   * 默认的存储级别都是仅在内存存储一份。在存储级别的末尾加上“2”表示持久化的数据存为两份。
   * cache() 底层其实就是调用了 persist()，并设置缓存级别 MEMORY_ONLY。
   *
   * 缓存级别的选择建议
   * （1）优先使用 MEMORY_ONLY，纯内存速度最快，而且没有序列化不需要消耗 CPU 进行反序列化操作，缺点就是比较耗内存。
   * （2）也可以使用 MEMORY_ONLY_SER，将数据进行序列化存储，纯内存操作还是非常快，只是在使用的时候需要消耗CPU进行反序列化。
   * （3）如果需要进行数据的快速失败恢复，那么就选择带后缀为 _2 的策略，进行数据的备份，这样在失败时，就不需要重新计算了。
   * （4）能不使用 DISK 相关的策略，就不要使用，因为有的时候，从磁盘读取数据，还不如重新计算一次。
   *
   * @param sc
   */
  def wordCountWithCache(sc: SparkContext): Unit = {

    val listRDD = sc.makeRDD(List("welcome to hangge.com", "hello world", "hangge.com"))
    val wordRDD: RDD[String] = listRDD.flatMap(_.split(" "))
    val mapRDD: RDD[(String, Int)] = wordRDD.map {
      word => {
        println("*** wordRDD.map ***")
        (word, 1)
      }
    }

    // 将RDD缓存到内存中
    mapRDD.cache()

    // 统计单词数量
    val reduceRDD = mapRDD.reduceByKey(_+_)
    reduceRDD.collect().foreach(println)
    println("-------------------------")

    // 单词分组
    val groupRDD = mapRDD.groupByKey()
    groupRDD.collect().foreach(println)

    //关闭 Spark
    sc.stop()
  }

  /**
   * CheckPoint： 检查点
   * 所谓的检查点其实就是通过将 RDD 中间结果写入磁盘。
   * 由于血缘依赖过长会造成容错成本过高，这样就不如在中间阶段做检查点容错，
   * 如果检查点之后有节点出现问题或应用程序重新计算时，可以从检查点开始重做血缘，减少了开销。
   *
   * 检查点流程：
   * 1. SparkContext 设置 checkpoint 目录，用于存放 checkpoint 的数据；
   *    对 RDD 调用 checkpoint 方法，然后它就会被 RDDCheckpointData 对象进行管理，此时这个 RDD 的 checkpoint 状态会被设置为 Initialized
   *
   * 2. 待 RDD 所在的 job 运行结束，会调用 job 中最后一个 RDD 的 doCheckpoint 方法，
   *  该方法沿着 RDD 的血缘关系向上查找被 checkpoint() 方法标记过的 RDD，并将其 checkpoint 状态从 Initialized 设置为 CheckpointingInProgress
   *
   * 3. 启动一个单独的 job，来将血缘关系中标记为 CheckpointInProgress 的 RDD 执行 checkpoint 操作，也就是将其数据写入 checkpoint 目录
   * 4. 将 RDD 数据写入 checkpoint 目录之后，会将 RDD 状态改变为 Checkpointed；并且还会改变 RDD 的血缘关系，即会清除掉 RDD 所有依赖的 RDD；最后还会设置其父 RDD 为新创建的 CheckpointRDD
   *
   * @param sc
   */
  def rddCheckPoint(sc: SparkContext): Unit = {
    // 设置检查点目录（如HDFS）
    sc.setCheckpointDir("./checkpoint1")

    // 创建RDD
    val listRDD = sc.makeRDD(List("welcome to hangge.com", "hello world", "hangge.com"))

    // 对RDD进行转换操作
    val wordRDD: RDD[String] = listRDD.flatMap(_.split(" "))
    val mapRDD: RDD[(String, Int)] = wordRDD.map {
      word => {
        println("*** wordRDD.map ***")
        (word, 1)
      }
    }

    // 设置检查点
    mapRDD.checkpoint()

    // 统计单词数量
    val reduceRDD = mapRDD.reduceByKey(_+_)
    reduceRDD.collect().foreach(println)
    println("-------------------------")

    // 单词分组
    val groupRDD = mapRDD.groupByKey()
    groupRDD.collect().foreach(println)


    /**
     * 缓存和检查点区别
     * 1） Cache 缓存只是将数据保存起来，不切断血缘依赖。 Checkpoint 检查点切断血缘依赖。
     * 2） Cache 缓存的数据通常存储在磁盘、内存等地方，可靠性低。 Checkpoint 的数据通常存储在HDFS 等容错、高可用的文件系统，可靠性高。
     * 3） 建议对checkpoint()的 RDD 使用Cache 缓存，这样 checkpoint 的 job 只需从 Cache 缓存中读取数据即可，否则需要再从头计算一次RDD。
     */
  }



  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Hello")
    val sc: SparkContext = new SparkContext(sparkConf)
    // wordCountWithCache(sc)
    rddCheckPoint(sc)

    sc.stop()
  }
}
