package com.zishi.spark.dataload.ss03

import breeze.linalg._
import breeze.numerics._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object ArrayAndMetrics {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Kmeans").setMaster("local")
    val sc = new SparkContext(conf)
    Logger.getRootLogger.setLevel(Level.WARN)

    println("---------------------------Breeze 创建函数-------------------------------")
    val m1 = DenseMatrix.zeros[Double](2,3)   // 2行3列的0矩阵
    val v1 = DenseVector.zeros[Double](3)     // 长度为3的0向量
    val v2 = DenseVector.ones[Double](3)      // 长度为3的1向量
    val v3 = DenseVector.fill(3)(5.0)         // 创建指定元素的向量，长度为3，元素为5
    val v4 = DenseVector.range(1,10,2)        // 根据范围创建向量参数（start，end，step），output： 1 3 5 7 9
    val m2 = DenseMatrix.eye[Double](3)       // 创建对角线为1的矩阵，3行3列的单元矩阵
    val m7 = diag(DenseVector(1.0,2.0,3.0))   // 创建指定对角线元素的矩阵，3行3列，对角元素分别为1 2 3
    val m3 = DenseMatrix((1.0,2.0),(3.0,4.0)) // 根据向量创建矩阵，每个数组就是一行
    val v8 = DenseVector(1,2,3,4)             // 根据元素创建向量
    // 转置
    val m8 = m3.t  // output: ((1 3) (2 4))
    println("m8: " + m8)
    // 根据下标创建向量和矩阵
    val v10 = DenseVector.tabulate(3){i=>2*i}               // output: 0 2 4
    val m4 = DenseMatrix.tabulate(3,2){case(i,j) => i+j}
    println("m4: " + m4)
    // 根据数组创建向量和矩阵
    val v11 = new DenseVector(Array(1,2,3,4))              // 结果同v8
    val m5 = new DenseMatrix(2,3,Array(11,12,12,21,21,11)) // 2行3列，元素分别为Array的元素
    // 创建随机向量和矩阵
    val v12 = DenseVector.rand(4)
    val m6 = DenseMatrix.rand(2,3)

    println("----------------------元素访问------------------------")
    val a = DenseVector(1,2,3,4,5,6,7,8,9) //元素访问
    val ao1 = a(0)            //访问指定的元素
    val ao2 = a(1 to 4)       //访问子元素向量, output: 2 3 4 5
    println("ao2: " + ao2)
    val ao3 = a(5 to 1 by -1) //指定起始和终止位置和补偿, output: DenseVector(6, 5, 4, 3, 2)
    println("ao3: " + ao3)
    val ao4 = a(1 to -1)      // -1代表索引最后的元素，output: DenseVector(2, 3, 4, 5, 6, 7, 8, 9)
    println("ao4: " + ao4)
    val ao5 = a(-1)           // 访问最后元素, output: 9

    val m = DenseMatrix((1.0,2.0,3.0),(4.0,5.0,6.0))
    val mo1 = m(0,1)  // 访问0行1列的元素，output：2
    println("mo1: ", mo1)
    val mo2 = m(::,1) // 访问第1列元素，output: DenseVector(2.0, 5.0)
    println("mo2: " + mo2)
    val mo21 = m.cols // 取矩阵列数，output：3
    val mo3 = m(1,::) // 访问第1行，output: Transpose(DenseVector(4.0, 5.0, 6.0))
    println("mo3: " + mo3)
    val mo31 = m.rows // 取矩阵行数，output：2

    // 元素操作
    val m_1 = DenseMatrix((1.0,2.0,3.0),(4.0,5.0,6.0))
    val m_1o1 = m_1.reshape(3,2)  // 变成3行2列的矩阵
    val m_1o2 = m_1.toDenseVector // 转换成向量, output: DenseVector(1.0, 4.0, 2.0, 5.0, 3.0, 6.0)
    println("m_1o2: " + m_1o2)

    val m_3 = DenseMatrix((1,2,3),(4,5,6),(7,8,9))
    val m_3o1 = lowerTriangular(m_3)// 取下三角，output：((1 0 0) (4  5  0) (7  8  9))
    println("m_3o1: " + m_3o1)
    val m_3o2 = upperTriangular(m_3)// 取上三角，output: ((1 2 3) (0 5 6) (0 0 9))
    println("m_3o2: " + m_3o2)
    val m_3o3 = m_3.copy            // copy生成一个新的矩阵
    val m_3o4 = diag(m_3)           // 对角线生成一个向量，output: DenseVector(1, 5, 9)
    println("m_3o4: " + m_3o4)
    m_3(::,2) := 5                  // 将第2列的元素全部改成5
    println("m_3: " + m_3)
    m_3(1 to 2,1 to 2) := 5
    println("m_3: " + m_3)

    // 矩阵的连接（向量的连接和矩阵类似）
    val a1 = DenseMatrix((1,2,3),(4,5,6))
    val a2 = DenseMatrix((1,1,1),(2,2,2))
    val a12V = DenseMatrix.vertcat(a1,a2) // 竖直的连接, output: ((1 2 3) (4 5 6) (1 1 1) (2 2 2))
    println("a12V: " + a12V)
    val a12H = DenseMatrix.horzcat(a1,a2) // 水平连接，output: ((1 2 3 1 1 1) (4 5 6 2 2 2))
    println("a12H: " + a12H)

    println("-------------------数值计算-----------------------")
    /*val a_3 = DenseMatrix((1,2,3),(4,5,6))
    val b_3 = DenseMatrix((1,1,1),(2,2,2))
    val ab31 = a_3 + b_3       // 对应元素相加
    println("ab31: " + ab31)
    val ab32 = a_3 :* b_3      // 对应元素相乘
    println("ab32: " + ab32)
    val ab33 = a_3 :/ b_3      // 对应元素取商，注意输出为整数商
    println("ab33: " + ab33)
    val ab34 = a_3 :< b_3      // 对应元素进行判断
    println("ab34: " + ab34)
    val ab35 = a_3 :== b_3     // 对应元素是否相等
    println("ab35: " + ab35)
    a_3 :+= 1                   // 所有元素+1，注意此时a_3已经改变
    println("a_3: " + a_3)
    a_3 :*= 2                   // 所有元素*2
    println("a_3: " + a_3)
    val aMax = max(a_3)         // 求最大值，output: 14
    println("aMax: " + aMax)
    val aMaxIndex = argmax(a_3) // 最大值位置的索引, output: (1, 2)
    println("aMaxIndex: " + aMaxIndex)
    val ab38 = DenseVector(1,2,3,4) dot DenseVector(1,1,1,1) // 内积，output: 10
    println("ab38: " + ab38)*/

    //------------------求和函数-----------------
    val a_4 = DenseMatrix((1,2,3),(4,5,6),(7,8,9))
    val a41 = sum(a_4)         // 所有元素求和，output: 45
    val a42 = sum(a_4,Axis._0) // 每一列进行求和, output: DenseVector(12, 15, 18)
    val a43 = sum(a_4,Axis._1) // 每一行进行求和, output: DenseVector(6, 15, 24)
    val a44 = trace(a_4)       // 对角线求和, output: 15
    val a45 = accumulate(DenseVector(1,2,3,4)) // 分别求前1,2,3,4个元素的和，output: DenseVector(1, 3, 6, 10)

    //------------------布尔函数--------------------
    val a_5 = DenseVector(true,false,true)
    val b_5 = DenseVector(false,true,true)
    //val ab51 = a_5 :& b_5 // 对应元素“与”
    //val ab52 = a_5 :| b_5 // 对应元素“或”
    val ab53 = !a_5       // 取“非”

    val a_5_2 = DenseVector(7, 8, 9)
    val ab54 = any(a_5_2)//任意一个元素 > 0 即为true
    val ab55 = all(a_5_2)//所有元素 > 0 则为true
  }
}