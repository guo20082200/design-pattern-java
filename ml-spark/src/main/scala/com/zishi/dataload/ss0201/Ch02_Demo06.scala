package com.zishi.dataload.ss0201

import org.apache.spark.sql.SparkSession

/**
 * RDD的行动操作
 */
object Ch02_Demo06 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("SparkDemo")
      .master("local[4]")
      .getOrCreate()

    //1. reduce
    val sc = spark.sparkContext
    val rddData1 = sc.parallelize(1 to 10, 2)

    // f: (T, T) => T
    def func(t1: Int, t2: Int): Int = {
      t1 + t2
    }

    //val i = rddData1.reduce(func)
    //val i = rddData1.reduce((t1: Int, t2: Int) => t1 + t2)
    //val i = rddData1.reduce((t1, t2) => t1 + t2)
    val i = rddData1.reduce(_ + _)
    println(i) //55

    //2. take
    println(rddData1.take(1).mkString("Array(", ", ", ")"))

    //3. count
    println(rddData1.count())

    // 4. first
    println(rddData1.first())

    //6. takeSample

    /**
     * 返回Rdd的子集，元素存入到一个Array里面
     * Return a fixed-size sampled subset of this RDD in an array
     * Params:
     * withReplacement – whether sampling is done with replacement
     * num – size of the returned sample
     * seed – seed for the random number generator
     * Returns:
     * sample of specified size in an array
     * Note:
     * this method should only be used if the resulting array is expected to be small, as all the data is loaded into the driver's memory.
     */
    val ints = rddData1.takeSample(false, 3)
    println(ints.mkString("Array(", ", ", ")")) // Array(3, 8, 7)


    // 7. takeOrdered
    val ints2 = rddData1.takeOrdered(4)
    println(ints2.mkString("Array(", ", ", ")"))

    // 8. saveAsTextFile

    // 9. countByKey, 对于一个（K，V）类型的RDD， 返回一个（K，Int）类型的map, Int为K出现的次数

    // 10. foreach

  }

}

