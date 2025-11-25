package com.zishi.spark.dataload.ss03

import breeze.linalg._


/**
 * breeze 数值计算函数
 */
object Ch03_a03 {


  def main(args: Array[String]): Unit = {

    def numericCal(): Unit = {
      //Breeze数值计算函数

      val a1: DenseMatrix[Double] = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0))
      val a2: DenseMatrix[Double] = DenseMatrix((7.0, 8.0, 9.0), (10.0, 11.0, 12.0))
      //1. 元素加法
      println(a1 + a2)

      /**
       * 8.0   10.0  12.0
       * 14.0  16.0  18.0
       */

      //2. 矩阵乘法
      println(a1 * a2.reshape(3, 2))

      /**
       * 51.0   65.0
       * 126.0  161.0
       */

      //3. 元素的乘法
      //a1 :*= a2
      //println(a1)

      /**
       * 7.0   16.0  27.0
       * 40.0  55.0  72.0
       */

      //4. 元素的除法
      // a1 :/=(a2)
      //println(a1)

      /**
       * 0.14285714285714285  0.25                 0.3333333333333333
       * 0.4                  0.45454545454545453  0.5
       */
      //5. 元素比较
      //  Element-wise less-than-or-equal-to comparator of this and b.
      // println(a1 <:= a2)

      /**
       * true  true  true
       * true  true  true
       */
      //6. 元素相等
      // Element-wise equality comparator of this and b
      //println(a1 :== a2)
      /**
       * false  false  false
       * false  false  false
       */

      //7. 元素追加
      //println(a1 :+= 2.0)

      /**
       * 3.0  4.0  5.0
       * 6.0  7.0  8.0
       */

      //8. 元素追乘
      println(a1 :*= 2.0)

      /**
       * 2.0  4.0   6.0
       * 8.0  10.0  12.0
       */

      // 9. 向量点积
      val vectorDot = DenseVector(1, 2, 3, 4) dot DenseVector(1, 1, 1, 1)
      println(vectorDot) //  10

      //元素最大值
      println(max(a1)) //16.0

      //元素最小值
      println(min(a1)) //6.0

      //元素最大值的位置
      println(argmax(a1)) // (1,2)

      //元素最小值的位置
      println(argmin(a1)) // (0,0)

    }

    // numericCal()
    def sumV(): Unit = {

      val m1 = DenseMatrix((1.0, 2.0, 3.0, 4.0), (5.0, 6.0, 7.0, 8.0), (9.0, 10.0, 11.0, 12.0))

      //元素求和
      println(sum(m1)) //78.0

      //每一列求和
      println(sum(m1, Axis._0)) //Transpose(DenseVector(15.0, 18.0, 21.0, 24.0))

      //每一行求和
      println(sum(m1, Axis._1)) // DenseVector(10.0, 26.0, 42.0)

      //对角线元素和
      println(trace(lowerTriangular(m1))) // 18.0

      //累积和
      val a3 = new DenseVector[Int](Array(10 to 20: _*))
      println(accumulate(a3)) // DenseVector(10, 21, 33, 46, 60, 75, 91, 108, 126, 145, 165)
    }
    // sumV()

    def boolFunc(): Unit = {
      //Breeze布尔函数
      val c = DenseVector(true, false, true)
      val d = DenseVector(false, true, true)
      //元素与操作
      println(c & d) // DenseVector(false, false, true)

      //元素或操作
      println(c | d) //DenseVector(true, true, true)

      //元素非操作
      println(!c) //DenseVector(false, true, false)

      val e = DenseVector[Int](-3, 0, 2)
      //存在非零元素
      println(any(e)) //true

      //所有元素非零
      println(all(e)) //false

    }
    //boolFunc()

    /**
     * Breeze线性代数函数
     */
    def linearFunc(): Unit = {


      val f = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0), (7.0, 8.0, 9.0))
      val g = DenseMatrix((1.0, 1.0, 1.0), (1.0, 1.0, 1.0), (1.0, 1.0, 1.0))

      //1. 线性求解，AX = B，求解X
      println(f \ g)

      /**
       * -2.5  -2.5  -2.5
       * 4.0   4.0   4.0
       * -1.5  -1.5  -1.5
       */


      //2. 转置
      println(f.t)

      /**
       * 1.0  4.0  7.0
       * 2.0  5.0  8.0
       * 3.0  6.0  9.0
       */

      //3. 求特征值
      println(det(f)) // 6.661338147750939E-16



      //4. 求逆
      println(inv(f))

      /**
       * -4.503599627370499E15  9.007199254740992E15    -4.503599627370495E15
       * 9.007199254740998E15   -1.8014398509481984E16  9.007199254740991E15
       * -4.503599627370498E15  9.007199254740992E15    -4.5035996273704955E15
       */


      //5. 求伪逆
      println(pinv(f))

      /**
       * -3.7720834019330525E14  7.544166803866101E14    -3.77208340193305E14
       * 7.544166803866092E14    -1.5088333607732208E15  7.544166803866108E14
       * -3.7720834019330406E14  7.544166803866104E14    -3.772083401933055E14
       */


      //特征值和特征向量

      println(eig(f))

      /**
       * Eig(DenseVector(16.116843969807043, -1.1168439698070427, -1.3036777264747022E-15),DenseVector(0.0, 0.0, 0.0),-0.23197068724628617  -0.7858302387420671   0.40824829046386363
       * -0.5253220933012336   -0.08675133925662833  -0.816496580927726
       * -0.8186734993561815   0.61232756022881      0.4082482904638625   )
       */


      println(".......................................")

      //奇异值分解

      val svd.SVD(u, s, v) = svd(g)

      println(u)

      /**
       * -0.5773502691896255  -0.5773502691896257  -0.5773502691896256
       * -0.5773502691896256  -0.2113248654051871  0.7886751345948126
       * -0.5773502691896256  0.7886751345948129   -0.21132486540518708
       */


      println("==============================")

      println(s) //DenseVector(3.0000000000000004, 0.0, 0.0)

      println("==============================")

      println(v)

      /**
       * -0.5773502691896256  -0.5773502691896257  -0.5773502691896256
       * 0.0                  -0.7071067811865474  0.7071067811865477
       * 0.816496580927726    -0.4082482904638629  -0.4082482904638628
       */


      //求矩阵的秩
      println(rank(f)) //2

      //矩阵长度
      println(f.size) //9

      //矩阵行数
      println(f.rows) // 3

      //矩阵列数
      println(f.cols) // 3

    }

    //linearFunc()


    /**
     * Breeze取整函数
     */

    def roundFunc(): Unit = {
      val h = DenseVector(-1.2, 0.7, 2.3)

      //四舍五入
      import breeze.numerics.round
      println(round(h)) // DenseVector(-1, 1, 2)

      //大于它的最小整数
      import breeze.numerics.ceil
      println(ceil(h)) // DenseVector(-1.0, 1.0, 3.0)

      //小于它的最大整数
      import breeze.numerics.floor
      println(floor(h)) // DenseVector(-2.0, 0.0, 2.0)
      //符号函数
      import breeze.numerics.signum
      println(signum(h)) // DenseVector(-1.0, 1.0, 1.0)

      //取正数
      import breeze.numerics.abs
      println(abs(h)) // DenseVector(1.2, 0.7, 2.3)

    }

    roundFunc()



  }

}
