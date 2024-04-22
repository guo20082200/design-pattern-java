package com.zishi.scala.a04.okk11

/**
 * Reduce 操作
 */
object Test15_HighLevelFunction_Reduce {
  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4)

    // 1. reduce
    println(list.reduce(_ + _))
    println(list.reduceLeft(_ + _))
    println(list.reduceRight(_ + _))

    println("===========================")

    val list2 = List(3, 4, 5, 8, 10)
    println(list2.reduce(_ - _)) // -24
    println(list2.reduceLeft(_ - _))
    println(list2.reduceRight(_ - _)) // 3 - (4 - (5 - (8 - 10))), 6
  }
}