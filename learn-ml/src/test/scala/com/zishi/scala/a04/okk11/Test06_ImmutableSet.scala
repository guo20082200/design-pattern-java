package com.zishi.scala.a04.okk11

/**
 * 不可变集合
 */
object Test06_ImmutableSet {
  def main(args: Array[String]): Unit = {
    // 1. 创建set
    val set1 = Set(13, 23, 53, 12, 13, 23, 78)
    println(set1)

    println("==================")

    // 2. 添加元素
    val set2 = set1 + 129
    println(set1)
    println(set2)
    println("==================")

    // 3. 合并set
    val set3 = Set(19, 13, 23, 53, 67, 99)
    val set4 = set2 ++ set3
    println(set2)
    println(set2.hashCode()) // 944062348

    println(set3)
    println(set4)
    println(set4.hashCode()) // -654809996

    // 4. 删除元素
    val set5 = set3 - 13
    println(set3) // HashSet(53, 13, 67, 99, 23, 19)
    println(set5) // HashSet(53, 67, 99, 23, 19)

    val set6 = set3.excl(23)
    println(set6) // HashSet(53, 13, 67, 99, 19)
  }
}