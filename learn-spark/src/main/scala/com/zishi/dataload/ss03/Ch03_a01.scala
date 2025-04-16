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

object Ch03_a01 {

  def main(args: Array[String]): Unit = {

    // 1. 全0矩阵
    val zerosMatrix = DenseMatrix.zeros[Double](2, 3)
    println(zerosMatrix)

    // 2. 全0向量
    val zerosVector = DenseVector.zeros[Double](2)
    println(zerosVector) // DenseVector(0.0, 0.0)

    // 3. 全1向量
    val onesVector = DenseVector.ones[Double](2)
    println(onesVector) // DenseVector(1.0, 1.0)

    // 4. 按数值填充向量
    val fillVector = DenseVector.fill[Double](2) {
      5.6
    }
    println(fillVector) // DenseVector(5.6, 5.6)

    //5. 生成随机向量
    val rangeDVector = DenseVector.rangeD(1.0, 10.8, step = .1)
    println(rangeDVector) // DenseVector(1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7000000000000002, 1.8, 1.9, 2.0, 2.1, 2.2,


    val rangeVector = DenseVector.range(1, 10, step = 1)
    println(rangeVector) //DenseVector(1, 2, 3, 4, 5, 6, 7, 8, 9)

    //6. 线性等分向量
    //DenseVector.l

    // 7。 单位矩阵
    val eye = DenseMatrix.eye[Double](dim = 4)
    println(eye)

    // 8. 对角矩阵
    val diagMatrix = diag(DenseVector(1, 2, 3))
    println(diagMatrix)

    // 9. 按照行创建矩阵
    val a = DenseMatrix((1.9, 2.1), (3.4, 5.0))
    println(a)

    // 10. 按照行创建向量
    val b = DenseVector(1, 2, 3, 4)
    println(b) // DenseVector(1, 2, 3, 4)

    // 11. 向量转置
    println(b.t) // Transpose(DenseVector(1, 2, 3, 4))

    //12. 从函数创建向量
    def func(i: Int): Int = {
      i * 2
    }

    //DenseVector.tabulate(3)(func)
    //DenseVector.tabulate(3)((i: Int)=> 2 * i)
    // DenseVector.tabulate(3)(i => 2 * i)
    val tabulateV = DenseVector.tabulate(size = 3)(_ * 2)
    println(tabulateV) // DenseVector(0, 2, 4)

    //13. 从函数创建矩阵
    //(f: (Int, Int) => V)
    def func2(a: Int, b: Int): Int = {
      a + b
    }

    //val tabulateM =  DenseMatrix.tabulate(rows = 4, cols = 5)(func2)
    //val tabulateM =  DenseMatrix.tabulate(rows = 4, cols = 5)((a: Int, b: Int) => a + b)
    //val tabulateM =  DenseMatrix.tabulate(rows = 4, cols = 5)((a, b) => a + b)
    val tabulateM = DenseMatrix.tabulate(rows = 4, cols = 5)(_ + _)
    println(tabulateM)

    //14. 从数组创建向量
    val c = new DenseVector(Array(1, 2, 3, 4))
    println(c) // DenseVector(1, 2, 3, 4)

    //15. 从数组创建矩阵
    val d = new DenseMatrix[Double](3,2, Array(3,4,5,6,7,8.0))
    println(d)

    //16. 0-1的随机向量
    // Creates a Vector of uniform random numbers in (0,1)
    // (size: Int, rand: Rand[T] = Rand.uniform)
    val randV = DenseVector.rand(10)
    println(randV) // DenseVector(0.8722629316281447, 0.4704135857820946, 0.5773390741233666, 0.15716573630823327, 0.6687449217580459, 0.8111096427085553, 0.8496420751401526, 0.5559367817142291, 0.6303282722868433, 0.1063606472102141)

    //17. 0-1的随机矩阵
    // (rows: Int, cols: Int, rand: Rand[T] = Rand.uniform)
    val randM = DenseMatrix.rand[Double](3,2)
    println(randM)

    /**
     * 0.4707807547905478   0.5774321365118862
     * 0.3756821586711623   0.6402021688117769
     * 0.43695145943896696  0.8110349687330063
     */

  }

}
