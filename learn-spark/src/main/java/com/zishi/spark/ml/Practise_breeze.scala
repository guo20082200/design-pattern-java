package com.zishi.spark.ml

import breeze.linalg._
import breeze.numerics._
import breeze.stats.distributions.Rand


object Practise_breeze {

  def main(args: Array[String]) {

    // 全0矩阵
    val matrix: DenseMatrix[Double] = DenseMatrix.zeros[Double](3, 2)
    //println(matrix)
    /**
     * 0.0  0.0
     * 0.0  0.0
     * 0.0  0.0
     */


    //全0向量
    val testVector: DenseVector[Double] = DenseVector.zeros[Double](2)
    //println(testVector) // DenseVector(0.0, 0.0)


    //全1向量
    val allOneVector = DenseVector.ones[Double](2)
    // println(allOneVector) // DenseVector(1.0, 1.0)


    //按数值填充向量
    val haveNumberFill = DenseVector.fill[Double](3, 2)
    // println(haveNumberFill) // DenseVector(2.0, 2.0, 2.0)


    // 按照范围和步长创建向量，整数，double类型，和float类型
    val rangeNUm = DenseVector.range(1, 10, 2) //DenseVector(1, 3, 5, 7, 9)
    val rangeNUmD = DenseVector.rangeD(1, 9, 2) //DenseVector(1.0, 3.0, 5.0, 7.0)
    val rangeNUmF = DenseVector.rangeF(1, 7, 2) //DenseVector(1.0, 3.0, 5.0)
    //println(rangeNUm) //DenseVector(1, 3, 5, 7, 9)
    //println(rangeNUmD) // DenseVector(1.0, 3.0, 5.0, 7.0)
    //println(rangeNUmF) // DenseVector(1.0, 3.0, 5.0)


    //单位矩阵
    val unitMatrix = DenseMatrix.eye[Double](4)
    //println(unitMatrix)
    /*
    1.0  0.0  0.0  0.0
    0.0  1.0  0.0  0.0
    0.0  0.0  1.0  0.0
    0.0  0.0  0.0  1.0
     */


    //对角矩阵
    val diagVector = diag(DenseVector(3.0, 4.0, 5.0))
    //println(diagVector)
    /*
    3.0  0.0  0.0
    0.0  4.0  0.0
    0.0  0.0  5.0
     */


    //按照行创建矩阵
    val byRowCreateMatrix = DenseMatrix((4.0, 5.0, 6.0), (7.0, 8.0, 9.0))
    //println(byRowCreateMatrix)
    /*
    4.0  5.0  6.0
    7.0  8.0  9.0
     */


    //按照行创建向量
    val denseCreateVector = DenseVector((4.0, 5.0, 6.0, 7.0, 8.0, 9.0))
    //println(denseCreateVector) ///DenseVector((4.0,5.0,6.0,7.0,8.0,9.0)


    //向量转置
    val vectorTranspose = DenseVector((4.0, 5.0, 6.0, 7.0, 8.0, 9.0)).t
    //println(vectorTranspose) //Transpose(DenseVector((4.0,5.0,6.0,7.0,8.0,9.0)))


    //从函数创建向量
    val funCreateVector = DenseVector.tabulate(5)(i => i * i)
    //println(funCreateVector) //DenseVector(0, 1, 4, 9, 16)
    val funCreateVector2 = DenseVector.tabulate(0 to 5)(i => i * i)
    //println(funCreateVector2) //DenseVector(0, 1, 4, 9, 16, 25)


    //从函数创建矩阵
    val createFuncMatrix = DenseMatrix.tabulate(3, 4) {
      case (i, j) => i * i + j * j
    }
    //println(createFuncMatrix)
    /*
       0  1  4  9
       1  2  5  10
       4  5  8  13
     */


    //从数组创建矩阵
    val createFunctionMatrix = new DenseMatrix[Double](3, 2, Array(1.0, 4.0, 7.0, 3.0, 6.0, 9.0))
    //println(createFunctionMatrix)

    /*
    1.0  3.0
    4.0  6.0
    7.0  9.0
     */


    //0 到 1的随机向量
    val formZeroToOneRandomVector = DenseVector.rand(9, Rand.uniform)
    //println(formZeroToOneRandomVector)
    // DenseVector(0.5600885250156584, 0.33244796946631383, 0.42634143780028877, 0.1643363249422698, 0.4626296365151068, 0.07931869907482358, 0.46303669420577687, 0.03413123410382801, 0.8355891441208354)

    val formZeroToOneRandomVector2 = DenseVector.rand(9, Rand.uniform)
    //println(formZeroToOneRandomVector2)
    //DenseVector(0.8576321584460136, 0.34101660244790777, 0.2032108483344055, 0.8226831708991271, 0.10832346489926081, 0.13012450948798926, 0.28452798973061344, 0.5530974738978829, 0.26206766014365246)


    //0 到 1 的随机矩阵， 均匀分布
    val formZeroToOneRandomMatrix = DenseMatrix.rand(3, 2, Rand.uniform)
    //println(formZeroToOneRandomMatrix)

    /*
    0.26599131224120254  0.29845662230566194
    0.5348565166529469   0.272567981702728
    0.5316909275719623   0.2926123397621956
     */

    //0 到 1 的随机矩阵， 高斯分布
    val formZeroToOneRandomMatrix2 = DenseMatrix.rand(3, 2, Rand.gaussian)
    //println(formZeroToOneRandomMatrix2)

    /*
      0.8854864966481104  0.6303964953195406
      0.9638791473185657  -0.37001686131478817
      1.2619008587150629  -0.5690805774673741
     */


    //Breeze 向量元素访问
    val a = new DenseVector[Int](Array(1 to 20: _*))
    // println(a) //DenseVector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)

    //指定位置
    //println(a(0)) //1

    //向量子集
    //println(a(1 to 4)) //DenseVector(2, 3, 4, 5)
    //println(a(1 until 4)) //DenseVector(2, 3, 4)

    //指定开始位置至结尾
    //println(a(1 to -1)) //DenseVector(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)

    //按照指定步长去子集  这个是倒序方式
    //println(a(5 to 0 by -1)) //DenseVector(6, 5, 4, 3, 2, 1)

    //最后一个元素
    //println(a(-1)) //20

    //Breeze 矩阵元素访问
    val m = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0))
    //println(m)

    //指定位置
    //println(m(0, 1)) //2.0

    //矩阵指定列
    //println(m(::, 1)) // DenseVector(2.0, 5.0)

    //Breeze元素操作
    //调整矩阵形状
    val justAdjustMatrix = m.reshape(3, 2)
    //println(justAdjustMatrix)

    /*
    1.0  5.0
    4.0  3.0
    2.0  6.0
     */


    //println(m)
    //矩阵转成向量
    val toVector = m.toDenseVector

    //println(toVector) //DenseVector(1.0, 4.0, 2.0, 5.0, 3.0, 6.0)
    // 向量转矩阵
    //println(toVector.toDenseMatrix) //1.0  4.0  2.0  5.0  3.0  6.0


    //println(m)
    /**
     * 1.0  2.0  3.0
     * 4.0  5.0  6.0
     */
    //复制下三角
    //println(lowerTriangular(m)) //
    /*
    1.0  0.0
    4.0  5.0
     */

    //复制上三角
    //println(upperTriangular(m))
    /*
    1.0  2.0
    0.0  5.0
     */

    //矩阵复制
    //println(m.copy)
    /**
     * 1.0  2.0  3.0
     * 4.0  5.0  6.0
     */

    //取对角线元素
    //println(diag(upperTriangular(m))) //DenseVector(1.0, 5.0)

    //子集赋数值
    //println(a) // DenseVector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
    //println(a(1 to 4) := 5) // DenseVector(5, 5, 5, 5)


    //子集赋向量
    //println(a(1 to 4) := DenseVector(1, 2, 3, 4)) //DenseVector(1, 2, 3, 4)
    //println(a) // DenseVector(1, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)

    //println(m)
    /**
     * 1.0  2.0  3.0
     * 4.0  5.0  6.0
     */

    //矩阵赋值
    //println( m( 1 to 2, 1 to 2) := 0.0 )

    //Exception in thread "main" java.lang.IndexOutOfBoundsException: Row slice of Range(1, 2) was bigger than matrix rows of 2

    //println(m(0 to 1, 1 to 2) := 0.0)
    /**
     * 0.0  0.0
     * 0.0  0.0
     */

    //println(m)
    /** 1.0  0.0  0.0
     * 4.0  0.0  0.0
     *
     */


    //矩阵列赋值

    val re = m(::, 2) := 5.0
    //println(re.toDenseMatrix) //5.0  5.0
    //println(m)
    /**
     * 1.0  2.0  5.0
     * 4.0  5.0  5.0
     */

    val a1 = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0))
    val a2 = DenseMatrix((7.0, 8.0, 9.0), (10.0, 11.0, 12.0))

    //垂直连接矩阵
    val verticalLike = DenseMatrix.vertcat(a1, a2)
    //println(verticalLike)

    /*
    1.0   2.0   3.0
    4.0   5.0   6.0
    7.0   8.0   9.0
    10.0  11.0  12.0
     */


    //横向连接矩阵
    val twoMatrixConn = DenseMatrix.horzcat(a1, a2)
    //println(twoMatrixConn)
    /*
    1.0  2.0  3.0  7.0   8.0   9.0
    4.0  5.0  6.0  10.0  11.0  12.0
     */

    //向量的连接
    val connectVector1 = DenseVector.vertcat(DenseVector(20, 21, 22), DenseVector(23, 24, 25))
    val connectVector2 = DenseVector.horzcat(DenseVector(20, 21, 22), DenseVector(23, 24, 25)) // 返回矩阵

    //println(connectVector1) //DenseVector(20, 21, 22, 23, 24, 25)
    //println(connectVector2.getClass) // class breeze.linalg.DenseMatrix$mcI$sp

    /*
      20  23
      21  24
      22  25
     */


    //Breeze数值计算函数
    //元素加法
    //println(a1)
    //println(a2)
    //println(a1 + a2)
    /*
      8.0   10.0  12.0
      14.0  16.0  18.0
     */
    //元素乘法，对应位置的元素相乘
    //println(a1.:*=(a2))

    /*
      7.0   16.0  27.0
      40.0  55.0  72.0
     */

    //元素除法 对应位置的元素相除
    //println(a2 :/= a1)
    /**
     * 7.0  4.0  3.0
     * 2.5  2.2  2.0
     */

    //元素比较
    //println(a1.<:<(a2))
    //println(a1 <:< a2)
    /**
     * true  true  true
     * true  true  true
     */

    //元素相等
    //println(a1 :== a2)
    /*
      false  false  false
      false  false  false
     */

    //元素追加
    //println(a1 :+= 2.0)

    /*
      3.0  4.0  5.0
      6.0  7.0  8.0
     */

    //元素追乘
    //println(a1 :*= 2.0)
    /*
      6.0   8.0   10.0
      12.0  14.0  16.0
     */

    //向量点积
    val vectorDot = DenseVector(1, 2, 3, 4) dot DenseVector(1, 1, 1, 1)
    //println(vectorDot) //10

    //元素最大值
     //println(max(a1))
    //元素最小值
    //println(min(a1))
    //元素最大值的位置
    //println(argmax(a1)) // (1,2)

    //元素最小值的位置
    //println(argmin(a1)) // (0,0)


    //Breeze求和函数
    val m1 = DenseMatrix((1.0, 2.0, 3.0, 4.0), (5.0, 6.0, 7.0, 8.0), (9.0, 10.0, 11.0, 12.0))
    //println(m1)


    //元素求和
    //println(sum(m1)) //78.0

    //每一列求和
    //println(sum(m1, Axis._0)) //Transpose(DenseVector(15.0, 18.0, 21.0, 24.0))
    //每一行求和
    //println(sum(m1, Axis._1)) //DenseVector(10.0, 26.0, 42.0)

    //对角线元素和
    //println(trace(lowerTriangular(m1))) // res61: Double = 18.0

    //累积和
    val a3 = new DenseVector[Int](Array(10 to 20: _*))
    //println(accumulate(a3)) // DenseVector(10, 21, 33, 46, 60, 75, 91, 108, 126, 145, 165)

    //Breeze布尔函数
    val c = DenseVector(true, false, true)

    val d = DenseVector(false, true, true)
    // 元素与操作
    //println(c &:& d) // DenseVector(false, false, true)

    // 元素或操作
    // println(c :| d) //DenseVector(true, true, true)

    // 元素非操作
    //println(!c) // DenseVector(false, true, false)
    val e = DenseVector[Int](-3, 0, 2)
    //存在非零元素
    //println(any(e)) //true
    //所有元素非零
    //println(all(e)) //false
    //Breeze线性代数函数
    val f = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0), (7.0, 8.0, 9.0))
    val g = DenseMatrix((1.0, 1.0, 1.0), (1.0, 1.0, 1.0), (1.0, 1.0, 1.0))
    //线性求解，AX = B，求解X
    //println(f \ g)

    /* -2.5  -2.5  -2.5
      4.0   4.0   4.0
      -1.5  -1.5  -1.5
     */


    //转置
    //println(f.t)
    /* breeze.linalg.DenseMatrix[Double] =
      1.0  4.0  7.0
      2.0  5.0  8.0
      3.0  6.0  9.0
     */

    //求特征值
    val f1 = DenseMatrix((2.0, 1.0, 0.0), (1.0, 2.0, 1.0), (0.0, 1.0, 2.0))
    //print(f1)

    // TODO： 为什么和计算的结果不一样？
    // println(det(f1)) // 4.0

    //求逆 TODO: 浮点数的精度如何保证？
    //println(inv(f1))

    /**
     * (3/4, -1/2, 1/4)
     * (-1/2, 1.0, -1/2)
     * (1/4, -2/4, 3/4)
     */
    /*
      0.75                  -0.49999999999999994  0.24999999999999994
      -0.49999999999999994  0.9999999999999999    -0.4999999999999999
      0.24999999999999994   -0.4999999999999999   0.7499999999999999
     */

    //求伪逆 TODO： 伪逆 和  逆 有什么关系？
    //println(pinv(f1))
    /*
      0.75                 -0.5000000000000006  0.2500000000000003
    -0.5000000000000002  1.0000000000000009   -0.5000000000000002
    0.25                 -0.5000000000000001  0.7499999999999999
     */

    //特征值和特征向量
    //println(eig(f1))
    /*
    Eig(DenseVector(3.4142135623730914, 1.9999999999999998, 0.5857864376269049),DenseVector(0.0, 0.0, 0.0),-0.4999999999999996  0.7071067811865478     0.4999999999999999
    -0.7071067811865475  4.059252933785728E-16  -0.7071067811865475
    -0.5000000000000003  -0.7071067811865474    0.4999999999999999   )
     */

    println("==============================")
    //奇异值分解
    print(g)
    val svd.SVD(u, s, v) = svd(g)
    println(u)
    /*
      -0.5773502691896255  -0.5773502691896257  -0.5773502691896256
      -0.5773502691896256  -0.2113248654051871  0.7886751345948126
      -0.5773502691896256  0.7886751345948129   -0.21132486540518708
     */
    println(s) //DenseVector(3.0000000000000004, 0.0, 0.0)
    println(v)
    println("==============================")

    /*
      -0.5773502691896256  -0.5773502691896257  -0.5773502691896256
      0.0                  -0.7071067811865474  0.7071067811865477
      0.816496580927726    -0.4082482904638629  -0.4082482904638628
     */

    //求矩阵的秩
    //println(rank(f)) //2

    //矩阵长度
    //println(f.size) //9

    //矩阵行数
    //println(f.rows) // 3

    //矩阵列数
    f.cols // 3

    //Breeze取整函数
    val h = DenseVector(-1.2, 0.7, 2.3) // breeze.linalg.DenseVector[Double] = DenseVector(-1.2, 0.7, 2.3)
    //四舍五入
    //println(round(h)) // DenseVector(-1, 1, 2)

    //大于它的最小整数
    //println(ceil(h)) // DenseVector(-1.0, 1.0, 3.0)

    //小于它的最大整数
    //println(floor(h)) // DenseVector(-2.0, 0.0, 2.0)

    //符号函数
    //println(signum(h)) // DenseVector(-1.0, 1.0, 1.0)

    //取正数
    //println(abs(h)) // DenseVector(1.2, 0.7, 2.3)


  }


}