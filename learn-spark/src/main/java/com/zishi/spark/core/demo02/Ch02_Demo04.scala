package com.zishi.spark.core.demo02

import org.apache.spark.sql.SparkSession

object Ch02_Demo04 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("SparkDemo")
      .master("local[4]")
      .getOrCreate()

    println("........................sortByKey")
    val initialScores = Array(("Fred", 88.0), ("Fred", 95.0), ("Fred", 91.0), ("Dilma", 93.0), ("Wilma", 95.0), ("Elma", 98.0))
    val rdd = spark.sparkContext.parallelize(initialScores)

    /**
     * def sortByKey(ascending: Boolean = true, numPartitions: Int = self.partitions.length)
     * ascending: 升序还是降序，默认是true，升序
     * numPartitions：并行任务数量
     */
    val value = rdd.sortByKey(ascending = true, 1)
    println(value.collect().mkString) // (Dilma,93.0)(Elma,98.0)(Fred,88.0)(Fred,95.0)(Fred,91.0)(Wilma,95.0)


    println("........................join")
    val initialScores2 = Array(("Fred", 77.0), ("Fred", 65.0), ("Elma", 98.0))
    val rdd2 = spark.sparkContext.parallelize(initialScores2) // (Elma,(98.0,98.0))(Fred,(88.0,77.0))(Fred,(88.0,65.0))(Fred,(95.0,77.0))(Fred,(95.0,65.0))(Fred,(91.0,77.0))(Fred,(91.0,65.0))

    /**
     * 笛卡尔积：相同的元素也会重复计算，没有相同的key的元素不展示在结果中
     */
    println(rdd.join(rdd2).collect().mkString)


    /**
     * -- For each key k in this or other1 or other2 or other3, return a resulting RDD that contains a tuple with the list of values for that key in this, other1, other2 and other3.
     * def cogroup[W1, W2, W3](other1: RDD[(K, W1)],
     * other2: RDD[(K, W2)],
     * other3: RDD[(K, W3)],
     * partitioner: Partitioner)
     * : RDD[(K, (Iterable[V], Iterable[W1], Iterable[W2], Iterable[W3]))]
     *
     * 针对在当前rdd或者other2，other3中的任何一个k，
     * 返回一个tuple，tuple的值是一个List，List是包含了k在每个rdd中的所有的值 (Fred,(Seq(88.0, 95.0, 91.0),Seq(77.0, 65.0))
     *
     *
     */
    val cogroupRdd = rdd.cogroup(rdd2)
    println(cogroupRdd.collect().mkString) // (Wilma,(Seq(95.0),Seq()))(Dilma,(Seq(93.0),Seq()))(Elma,(Seq(98.0),Seq(98.0)))(Fred,(Seq(88.0, 95.0, 91.0),Seq(77.0, 65.0)))


    println("........................cartesian")
    /**
     * Return the Cartesian product of this RDD and another one, that is, the RDD of all pairs of elements (a, b) where a is in this and b is in other.
     * 返回rdd和另一个rdd的笛卡尔集：
     * 返回的rdd中每一对元素(a, b)，a在当前的rdd中，b在另一个rdd中
     */
    val cartesianRdd = rdd.cartesian(rdd2)
    println(cartesianRdd.collect().mkString)
    // 输出结果：((Fred,88.0),(Fred,77.0))((Fred,88.0),(Fred,65.0))((Fred,88.0),(Elma,98.0))((Fred,95.0),(Fred,77.0))((Fred,91.0),(Fred,77.0))((Fred,95.0),(Fred,65.0))((Fred,91.0),(Fred,65.0))((Fred,95.0),(Elma,98.0))((Fred,91.0),(Elma,98.0))((Dilma,93.0),(Fred,77.0))((Dilma,93.0),(Fred,65.0))((Dilma,93.0),(Elma,98.0))((Wilma,95.0),(Fred,77.0))((Elma,98.0),(Fred,77.0))((Wilma,95.0),(Fred,65.0))((Elma,98.0),(Fred,65.0))((Wilma,95.0),(Elma,98.0))((Elma,98.0),(Elma,98.0))

    val a = Array(1, 2, 3, 3)
    val b = Array(3, 3, 4, 5)
    val da = spark.sparkContext.parallelize(a)
    val db = spark.sparkContext.parallelize(b)
    println(da.cartesian(db).collect().mkString) // (1,3)(1,4)(1,5)(2,3)(2,4)(2,5)(3,3)(3,4)(3,5)

    //
    println("........................subtractRdd")
    /**
     * Return an RDD with the elements from this that are not in other.
     *  Uses this partitioner/partition size, because even if other is huge, the resulting RDD will be <= us.
     *
     *  返回一个rdd，rdd中的元素在this中，但是不能在other中
     */
    val subtractRdd = da.subtract(db)
    println("subtractRdd:" + subtractRdd.collect().mkString("Array(", ", ", ")")) // subtractRdd:Array(1, 2)

    println("........................pipe") // 略去：shell终端执行的


    println("........................randomSplit") // 略去：shell终端执行的

    /**
     * def randomSplit(
     * weights: Array[Double],
     * seed: Long = Utils.random.nextLong): Array[RDD[T]]
     * 1. 按照指定的权重随机分隔RDD
     * weights： 切割的权重数组，如果sum不等于1，那么将会被归一化为1
     * seed： 随机种子
     * 返回：一个数组，里面的元素是切割之后的RDD
     * weights – weights for splits, will be normalized if they don't sum to 1
     *
     *
     * 该方法常用于样本的划分
     */

    val dd = spark.sparkContext.parallelize(1 to 100)
    val randomSplitArr = dd.randomSplit(Array(0.1, 0.2, 0.3, 0.4))
    for (elem <- randomSplitArr) {
      println(elem.collect().mkString("Array(", ", ", ")"))
    }

    /**
     * 输出结果如下：
     * Array(11, 46, 55, 68, 90)
     * Array(8, 13, 28, 30, 31, 32, 33, 40, 41, 42, 44, 47, 50, 52, 63, 65, 67, 70, 72, 84, 91, 100)
     * Array(1, 5, 6, 7, 9, 15, 18, 20, 22, 23, 27, 34, 35, 36, 43, 53, 57, 58, 60, 61, 66, 71, 73, 74, 75, 76, 80, 85, 86, 93, 94, 99)
     * Array(2, 3, 4, 10, 12, 14, 16, 17, 19, 21, 24, 25, 26, 29, 37, 38, 39, 45, 48, 49, 51, 54, 56, 59, 62, 64, 69, 77, 78, 79, 81, 82, 83, 87, 88, 89, 92, 95, 96, 97, 98)
     */


  }

}

