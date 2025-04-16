package com.zishi.dataload.ss03

/**
 * Breeze介绍
 * Breeze： 机器学习和数值计算库
 * Epic： 高性能统计分析器和结构化预测库
 * Puck： 快速GPU加速解析器
 *
 */

// 导包

import breeze.linalg._

/**
 * 向量和矩阵的元素访问，和元素的操作
 */
object Ch03_a02 {

  def main(args: Array[String]): Unit = {

    /**
     * 矩阵和向量元素的访问
     */
    def access(): Unit = {
      val a = DenseVector(1, 2, 3, 4, 6, 7, 8, 9)
      //a(1)
      // 取子集
      println(a(0 to 2)) //DenseVector(1, 2, 3)
      println(a(0 until 2)) //DenseVector(1, 2)
      println(a.slice(1, 4)) //DenseVector(2, 3, 4)
      println(a(5 to 0 by -1)) // DenseVector(7, 6, 4, 3, 2, 1)
      // 从指定位置到向量的结尾
      println(a(1 to -1)) //DenseVector(2, 3, 4, 6, 7, 8, 9)
      println(a(-1)) // 最后一个元素

      // 矩阵指定列
      val d = new DenseMatrix[Double](3, 2, Array(3, 4, 5, 6, 7, 8.0))
      println(d)
      println(d(::, 1)) //矩阵d的第二列，DenseVector(6.0, 7.0, 8.0)
    }

    /**
     * 矩阵和向量元素的操作
     */
    def operator(): Unit = {

      val m = DenseMatrix((1, 2, 3), (4, 5, 6), (7, 8, 9), (10, 11, 12))
      println(m)
      /**
       * 1   2   3
       * 4   5   6
       * 7   8   9
       * 10  11  12
       */

      //1. 调整矩阵形状 形状变成3行4列
      // 矩阵变形时也是按照列从上到下从左到右依次填满矩阵的。
      val reshape = m.reshape(3, 4)
      println(reshape)
      /**
       * 1  10  8   6
       * 4  2   11  9
       * 7  5   3   12
       */

      //2. 矩阵转vector向量
      val denseVector = m.toDenseVector
      // 注意一下这个结果，将矩阵转为向量时，是按照列的顺序依次获取所有元素组成一个向量的。
      println(denseVector) // DenseVector(1, 4, 7, 10, 2, 5, 8, 11, 3, 6, 9, 12)

      //3. 复制下三角, 矩阵是方阵
      val m1 = DenseMatrix((1, 2, 3), (4, 5, 6), (7, 8, 9))
      val lowerTriang = lowerTriangular(m1)
      println("》》》》》》》》》》》》》\n" + lowerTriang)
      /**
       * 1  0  0
       * 4  5  0
       * 7  8  9
       */

      //4. 复制上三角
      val upperTriang = upperTriangular(m1)
      println("》》》》》》》》》》》》》\n" + upperTriang)
      /**
       * 1  2  3
       * 0  5  6
       * 0  0  9
       */
      //5. 矩阵复制
      println(m1.copy)
      /**
       * 1  2  3
       * 4  5  6
       * 7  8  9
       */
      //6. 取对角线元素
      //val diagM = diag(m)
      //println(diagM) // requirement failed: m must be square,取对角线的矩阵一定是方阵
      val diagM1 = diag(m1)
      println(diagM1) // DenseVector(1, 5, 9)
      //7. 子集赋数值
      val a = DenseVector(1, 2, 3, 4, 6, 7, 8, 9)
      a(1 to 5) := 5
      println(a) // DenseVector(1, 5, 5, 5, 5, 5, 8, 9)

      //8. 子集赋向量
      a(1 to 5) := DenseVector(19, 20, 37, 49, 100)
      println(a) // DenseVector(1, 19, 20, 37, 49, 100, 8, 9)
      //9. 矩阵赋值 矩阵赋值，从第二行到第三行，第二列到第三列赋值为5
      m1(1 to 2, 1 to 2) := 5
      println(m1)
      /**
       * 1  2  3
       * 4  5  5
       * 7  5  5
       */
      //10. 矩阵列赋值， 将第三列的值变成5
      m1(::, 2) := 9
      println(m1)

      /**
       * 1  2  9
       * 4  5  9
       * 7  5  9
       */

      //11. 垂直连接矩阵
      val a1 = DenseMatrix((10, 20, 30), (40, 50, 60))
      val a2 = DenseMatrix((7, 8, 9))
      val a1a2 = DenseMatrix.vertcat(a1, a2)
      println(a1a2)

      /**
       * 10  20  30
       * 40  50  60
       * 7   8   9
       */
      //12. 横向连接矩阵
      val b1 = DenseMatrix((1, 2, 3), (4, 5, 6))
      val b2 = DenseMatrix((7, 8, 9), (10, 11, 12))
      val b1b2 = DenseMatrix.horzcat(b1, b2)
      println(b1b2)
      println("rows:" + b1b2.rows + ", cols:" + b1b2.cols)// rows:2, cols:6

      /**
       * 1  2  3  7   8   9
       * 4  5  6  10  11  12
       */
      //13. 向量连接
      val v1 = DenseVector(1, 2)
      val v2 = DenseVector(15, 26)
      val vercat = DenseVector.vertcat(v1, v2)
      println(vercat) // DenseVector(1, 2, 15, 26)

    }

    operator()

  }

}
