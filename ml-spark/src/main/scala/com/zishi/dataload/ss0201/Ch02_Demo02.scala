package com.zishi.dataload.ss0201

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object Ch02_Demo02 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("SparkDemo")
      .master("local[4]")
      .getOrCreate()

    /**
     * union: 两个Rdd的合并，相同的元素出现多次，可以使用 distinct消除重复元素
     */
    println("........................union")
    val rdd1 = spark.sparkContext.parallelize(1 to 10, 3)

    //查看分区的内容
    println(rdd1.glom().collect().mkString("Array(", ", ", ")")) // Array([I@76c86567, [I@7e5efcab, [I@5a4dda2)

    val rdd2 = spark.sparkContext.parallelize(-3 to 5, 3)
    val rdd3 = rdd1.union(rdd2)
    println(rdd3.collect().mkString("Array(", ", ", ")")) // 结果就是rdd2的元素逐个追加到rdd1里面，元素会重复
    println(rdd3.distinct().collect().mkString("Array(", ", ", ")")) // Array(0, 6, 1, 7, 8, 2, -3, 3, 9, -2, 4, 10, -1, 5)


    /**
     * intersection：
     * 取两个rdd的交集，
     * 输出的rdd不包含任何重复元素，即便原来的集合中有重复的元素
     * 注意:该方法内部会执行shuffle
     */
    println("........................intersection")
    val rdd4 = rdd1.intersection(rdd2)
    println(rdd4.collect().mkString("Array(", ", ", ")")) // Array(3, 4, 1, 5, 2)

    println("........................groupBy")
    val rdd = spark.sparkContext.parallelize(Seq(("A", 1), ("A", 3), ("B", 4), ("B", 2), ("C", 5)))
    val groupBy = rdd.groupBy(_._1) // 没有groupByKey这个函数了
    groupBy.collect().foreach(println)

    /**
     * 输出结果如下：
     * (A,Seq((A,1), (A,3)))
     * (B,Seq((B,4), (B,2)))
     * (C,Seq((C,5)))
     */
    // func: (V, V) => V
    println("........................reduceByKey")

    def func(v1: Int, v2: Int): Int = v1 + v2

    // def func2: (Int, Int) => Int = (v1: Int, v2: Int) => v1 + v2
    val reduceByKeyRdd = rdd.reduceByKey(func)
    reduceByKeyRdd.collect().foreach(println)

    /**
     * 输出结果如下：
     * (A,4)
     * (B,6)
     * (C,5)
     */
    println("........................aggregate")

    /**
     * def aggregate[U: ClassTag](zeroValue: U)(seqOp: (U, T) => U, combOp: (U, U) => U): U = withScope
     * seqOp的操作是遍历分区中的所有元素(T)，第一个T跟zeroValue做操作，结果再作为与第二个T做操作的zeroValue，直到遍历完整个分区
     * combOp操作是把各分区聚合的结果，再聚合
     *
     * 参考：https://zhuanlan.zhihu.com/p/406296698
     *
     * @param s1
     * @param s2
     * @return
     */
    def seqOp(s1: Int, s2: Int): Int = {
      println("seq计算时的输入值: " + s1 + ":" + s2)
      s1 + s2
    }

    def combOp(c1: Int, c2: Int): Int = {
      println("comb的计算时的输入值: " + c1 + ":" + c2)
      c1 + c2
    }

    val aggRdd: RDD[Int] = spark.sparkContext.parallelize(1 to 4).repartition(2)
    val res: Int = aggRdd.aggregate(0)(seqOp, combOp)
    println(res)

    /**
     * 输出结果如下：
     * seq计算时的输入值: 0:3
     * seq计算时的输入值: 0:1
     * seq计算时的输入值: 3:4
     * seq计算时的输入值: 1:2
     * comb的计算时的输入值: 0:7
     * comb的计算时的输入值: 7:3
     * 10
     */

    val res2: Int = aggRdd.aggregate(2)(seqOp, combOp)
    println(res2)

    /**
     * 输出结果如下：
     * seq计算时的输入值: 2:1
     * seq计算时的输入值: 2:3
     * seq计算时的输入值: 3:2
     * seq计算时的输入值: 5:4
     * comb的计算时的输入值: 2:9
     * comb的计算时的输入值: 11:5
     * 16
     *
     */

    println("........................treeAggregate")

    /**
     * treeAggregate:
     * def treeAggregate[U: ClassTag](zeroValue: U)(
     * seqOp: (U, T) => U,
     * combOp: (U, U) => U,
     * depth: Int = 2): U = withScope {
     * treeAggregate(zeroValue, seqOp, combOp, depth, finalAggregateOnExecutor = false)
     * }
     *
     * 与aggregate不同的是treeAggregate多了depth的参数，其他参数含义相同。
     * aggregate在执行完SeqOp后会将计算结果拿到driver端使用CombOp遍历一次SeqOp计算的结果，最终得到聚合结果。
     * 而treeAggregate不会一次就Comb得到最终结果，SeqOp得到的结果也许很大，直接拉到driver可能会OutOfMemory，
     * 因此它会先把分区的结果做局部聚合(reduceByKey)，如果分区数过多时会做分区合并，之后再把结果拿到driver端做reduce。
     *
     * 注：与aggregate不同的地方是：在每个分区，会做两次或者多次combOp，避免将所有局部的值传给driver端。另外，初始值zeroValue不会参与combOp。
     *
     */
    val aggRdd2: RDD[Int] = spark.sparkContext.parallelize(1 to 8).repartition(4)

    /**
     * 分区数据如下：
     * Array(7)
     *  Array(5, 8)
     *  Array(2, 4)
     *  Array(1, 3, 6)
     */
    val value: RDD[Array[Int]] = aggRdd2.glom()

    def f(ele: Array[Int]): Unit = {
      println(ele.mkString("Array(", ", ", ")"))
    }

    value.foreach(f)
    val res3: Int = aggRdd2.treeAggregate(zeroValue = 0)(seqOp, combOp)
    println(res3)

    /**
     * 输出结果如下（每次运行的结果不一样）：
     *
     * 在driver端进行得计算
     * seq计算时的输入值: 0:2
     * seq计算时的输入值: 0:1
     * seq计算时的输入值: 0:7
     * seq计算时的输入值: 0:5
     * comb的计算时的输入值: 0:7: -- 针对每个partition做了一次comb计算
     * seq计算时的输入值: 2:4
     * seq计算时的输入值: 1:3
     * seq计算时的输入值: 5:8
     * comb的计算时的输入值: 0:6 -- 针对每个partition做了一次comb计算
     * comb的计算时的输入值: 0:13 -- 针对每个partition做了一次comb计算
     * seq计算时的输入值: 4:6
     * comb的计算时的输入值: 0:10 -- 针对每个partition做了一次comb计算
     * comb的计算时的输入值: 0:10
     * comb的计算时的输入值: 10:13
     * comb的计算时的输入值: 23:7
     * comb的计算时的输入值: 30:6
     * 36
     *
     */
    println("........................aggregateByKey")

    /**
     * def aggregateByKey[U: ClassTag](zeroValue: U, partitioner: Partitioner)(seqOp: (U, V), combOp: (U, U) => U): RDD[(K, U)] = self.withScope {
     */
    def seqOp2(s1: Int, s2: Int): Int = {
      println("seq2计算时的输入值: " + s1 + ":" + s2)
      math.max(s1, s2)
    }

    def combOp2(c1: Int, c2: Int): Int = {
      println("comb2的计算时的输入值: " + c1 + ":" + c2)
      c1 + c2
    }

    println(rdd.collect().mkString("Array(", ", ", ")")) // Array((A,1), (A,3), (B,4), (B,2), (C,5))
    //val aggregateByKeyRdd = rdd.aggregateByKey(zeroValue = 0)(seqOp2, combOp2)
    //val aggregateByKeyRdd = rdd.aggregateByKey(zeroValue = 0)((s1: Int, s2: Int) => math.max(s1, s2), (c1: Int, c2: Int) => c1 + c2)
    //val aggregateByKeyRdd = rdd.aggregateByKey(zeroValue = 0)((s1, s2) => math.max(s1, s2), (c1, c2) => c1 + c2)
    // val aggregateByKeyRdd = rdd.aggregateByKey(zeroValue = 0)(math.max(_, _), _ + _)
    val aggregateByKeyRdd = rdd.aggregateByKey(zeroValue = 0)(math.max, _ + _)
    println(aggregateByKeyRdd.collect().mkString("Array(", ", ", ")")) // Array((A,4), (B,6), (C,5))
  }

}

