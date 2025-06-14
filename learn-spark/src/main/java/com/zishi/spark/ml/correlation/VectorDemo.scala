package com.zishi.spark.ml.correlation

import org.apache.spark.ml.linalg.{DenseVector, SparseVector}
import org.apache.spark.sql.SparkSession

object VectorDemo {

  def main(args: Array[String]): Unit = {
    // sparseVectorDemo01()
    denseVectorDemo01()
  }

  /**
   * SparseVector 代表：索引数组和值数组
   * A sparse vector represented by an index array and a value array.
   *
   * size vector的大小
   * indices 索引数组, 假设严格递增
   * values 值数组, 和索引数组的大小一样
   *
   * 优点：
   * 内存效率高：只存储非零元素，适合稀疏数据。
   * 计算优化：某些操作（如点积）只处理非零元素。
   */
  def sparseVectorDemo01(): Unit = {
    // 创建一个长度为 10 的稀疏向量，索引 1 和 3 处有非零值
    val sparseVec = new SparseVector(10, Array(1, 3), Array(3.0, 4.0))
    println(sparseVec)
    // 这里标识向量：[0.0, 3.0, 0.0, 4.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
  }

  /**
   * 当向量中非零元素比例较高或需要频繁随机访问元素时，使用 DenseVector 更高效。
   */
  def denseVectorDemo01(): Unit = {
    // 创建一个长度为 10 的DenseVector
    val denseVector = new DenseVector(Array(1.0, 0.0, 3.0, 0.0, 5.0))
    println(denseVector)
    // 这里标识向量：[1.0,0.0,3.0,0.0,5.0]
  }


}
