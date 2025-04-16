package com.zishi.dataload.ss0201

import org.apache.spark.sql.SparkSession

object Ch02_Demo01 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("SparkDemo")
      .master("local[4]")
      .getOrCreate()

    val data = Array.apply(1, 2, 3, 4, 5, 6, 7, 8, 9)

    /**
     * 1. rdd 创建
     */
    val rdd = spark.sparkContext.parallelize(data, numSlices = 3)
    println(rdd.getClass) //org.apache.spark.rdd.ParallelCollectionRDD
    println(rdd.collect().mkString("Array(", ", ", ")"))

    /**
     * 2. map 操作
     */
    // val mapRdd = rdd.map(x => x * 2)
    val mapRdd = rdd.map(_ * 2)
    println(mapRdd.getClass) // org.apache.spark.rdd.MapPartitionsRDD

    println(mapRdd.collect().mkString("Array(", ", ", ")")) //Array(2, 4, 6, 8, 10, 12, 14, 16, 18)

    /**
     * 3. filter 操作
     */
    val filterRdd = rdd.filter(_ > 7)
    println(filterRdd.collect().mkString("Array(", ", ", ")")) // Array(8, 9)


    /**
     * 4. flatMap 操作
     */
    val flatMapRdd = filterRdd.flatMap(x => {
      val data = x to 12
      data
    })
    println(flatMapRdd.collect().mkString("Array(", ", ", ")")) // Array(8, 9, 10, 11, 12, 9, 10, 11, 12)

    /**
     * 5. mapPartitions 操作
     *
     * rdd.partitions: 返回的Array数组，数组里面的元素是：org.apache.spark.rdd.ParallelCollectionPartition对象
     *
     * map 和 MapPartitions的区别：
     * map: 比如一个partition中有1万条数据；那么你的function要执行和计算1万次。
     * MapPartitions:一个task仅仅会执行一次function，function一次接收所有的partition数据。只要执行一次就可以了，性能比较高。
     *
     *
     * MapPartitions的缺点：一定是有的。
     * 如果是普通的map操作，一次function的执行就处理一条数据；
     * 那么如果内存不够用的情况下，比如处理了1千条数据了，那么这个时候内存不够了，
     * 那么就可以将已经处理完的1千条数据从内存里面垃圾回收掉，或者用其他方法，腾出空间来吧。所以说普通的map操作通常不会导致内存的OOM异常。
     *
     * 但是MapPartitions操作，对于大量数据来说，比如甚至一个partition，100万数据，一次传入一个function以后，
     * 那么可能一下子内存不够，但是又没有办法去腾出内存空间来，可能就OOM，内存溢出。
     *
     *
     * 整理：
     * map是处理集合中的每个元素
     * mapPartitions：是把一个分区的数据当作一个整体来处理,
     *
     * 当map里面有比较耗时的操作时（譬如说：链接数据库），就可以使用mapPartitions，每个分区和数据库交互一次
     */
    println(rdd.partitions.mkString("Array(", ", ", ")"))
    println(rdd.partitions(1).getClass) //org.apache.spark.rdd.ParallelCollectionPartition
    println(rdd.partitions(1).toString)
    println(rdd.partitions(1).index)


    println("............................")

    def myFunc[T](iter: Iterator[T]): Iterator[(T, T)] = {
      var res = List[(T, T)]()
      var pre = iter.next()
      //println("......" + pre)
      while (iter.hasNext) {
        val curr = iter.next()
        res.::=(pre, curr)
        //println(res)
        pre = curr
      }
      res.iterator
    }

    val mapRdd2 = rdd.mapPartitions(myFunc, preservesPartitioning = true)
    // Array((2,3), (1,2), (5,6), (4,5), (8,9), (7,8))
    println(mapRdd2.collect().mkString("Array(", ", ", ")"))


    /**
     * f: (Int, Iterator[T]) => Iterator[U]
     * 6. mapPartitionsWithIndex
     */
    def myMapPartitionsWithIndexFunc[T](i: Int, iter: Iterator[T]): Iterator[(T, T)] = {
      var res = List[(T, T)]()
      if(i == 1) { // 只是处理了分区1的数据
        var pre = iter.next()
        //println("......" + pre)
        while (iter.hasNext) {
          val curr = iter.next()
          res.::=(pre, curr)
          //println(res)
          pre = curr
        }
      }
      res.iterator
    }

    val withIndexRdd = rdd.mapPartitionsWithIndex(myMapPartitionsWithIndexFunc)
    println(withIndexRdd.collect().mkString("Array(", ", ", ")")) // Array((5,6), (4,5))


    /**
     * f: (Int, Iterator[T]) => Iterator[U]
     * 6. mapPartitionsWithIndex
     */

    /**
     * 7. sample 函数有三个参数：
     * a. withReplacement(Boolean):抽取数据后是否将数据返回 true（放回），false（丢弃）
     * b. fraction(Double):
     *    如果抽取不放回的场合：则表示数据源中每条数据被抽取的概率，
     *    如果抽取放回的场合：表示数据源中的每条数据被抽取的可能次数
     * c. seed(Long):抽取数据时随机算法的种子,如果传递参数，则运行时每次抽取的数据是一样的，如果不传递参数，则每次运行时结果是不一样的。
     *
     * sample函数的使用场景：
     * 主要出现数据倾斜时，使用该函数，数据倾斜主要发生在有shuffle操作时。
     * 数据重新整理后，可能会出现一个区的数据量很少，另一个数据量很大的情况，这种情况称为“数据倾斜”，
     * 导致一个节点的计算量较大，一个计算节点计算量较少，影响最终的处理效率。
     * 为了避免这种情况，可以在shuffle之前，进行sample操作，查看数据源中那些数据较多，提取处理。
     */
    val rddData = spark.sparkContext.parallelize(1 to 30, 3)
    // Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30)


    //TODO： 不放回抽取数据
    val sampleRdd = rddData.sample( withReplacement = false, fraction = 0.4, seed = 1)
    println(sampleRdd.collect().mkString("Array(", ", ", ")")) // 多次运行结果一样

    val sampleRdd2 = rddData.sample(withReplacement = false, fraction = 0.4)
    println(sampleRdd2.collect().mkString("Array(", ", ", ")")) // 多次运行结果不一样

    //TODO： 放回抽取数据
    val sampleRdd3 = rddData.sample(withReplacement = true, fraction = 3, seed = 1)
    println(sampleRdd3.collect().mkString("Array(", ", ", ")"))
    // 第一次运行：Array(1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 4, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 11, 11, 12, 12, 12, 12, 13, 13, 14, 14, 14, 14, 15, 15, 16, 16, 17, 17, 17, 17, 18, 18, 18, 18, 18, 19, 19, 20, 20, 20, 20, 20, 21, 21, 21, 21, 21, 22, 22, 22, 23, 23, 23, 24, 25, 25, 25, 25, 26, 26, 26, 27, 27, 27, 28, 28, 28, 28, 29, 29, 29, 29, 30, 30, 30, 30)
    // 第二次运行：Array(1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 4, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 11, 11, 12, 12, 12, 12, 13, 13, 14, 14, 14, 14, 15, 15, 16, 16, 17, 17, 17, 17, 18, 18, 18, 18, 18, 19, 19, 20, 20, 20, 20, 20, 21, 21, 21, 21, 21, 22, 22, 22, 23, 23, 23, 24, 25, 25, 25, 25, 26, 26, 26, 27, 27, 27, 28, 28, 28, 28, 29, 29, 29, 29, 30, 30, 30, 30)
    val sampleRdd4 = rddData.sample(withReplacement = true, fraction = 3)
    println(sampleRdd4.collect().mkString("Array(", ", ", ")"))
    //第一次运行：Array(1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 5, 6, 6, 6, 6, 6, 7, 7, 7, 8, 8, 9, 9, 10, 10, 10, 11, 12, 12, 12, 13, 13, 13, 13, 14, 14, 14, 14, 15, 16, 16, 16, 17, 17, 17, 18, 18, 18, 18, 19, 20, 20, 21, 21, 21, 22, 22, 22, 23, 23, 23, 23, 24, 24, 25, 25, 25, 25, 25, 25, 26, 26, 27, 27, 28, 28, 29, 29, 29, 30, 30, 30, 30, 30)
    //第二次运行：Array(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 7, 8, 8, 8, 8, 9, 9, 10, 10, 10, 10, 11, 11, 11, 12, 12, 13, 13, 13, 14, 15, 15, 15, 15, 17, 17, 18, 19, 19, 19, 20, 20, 20, 20, 21, 22, 24, 24, 24, 25, 25, 25, 25, 25, 25, 26, 26, 27, 27, 28, 28, 29, 29, 29, 29, 29, 30)


    println("union.......")
    //MLUtils.

    //spark.close()

  }

}

