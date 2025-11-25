//package com.zishi.spark.dataload.ss03
//
//import org.apache.spark.ml.linalg.Vectors
//import org.apache.spark.rdd.RDD
//import org.apache.spark.{SparkConf, SparkContext}
//
//
///**
// * 分布式矩阵
// *
// * 行矩阵
// * 索引行矩阵
// * 坐标矩阵
// * 块矩阵
// */
//object Ch03_a04 {
//
//
//  def main(args: Array[String]): Unit = {
//
//    val conf = new SparkConf() //创建环境变量
//      .setMaster("local") //设置本地化处理
//      .setAppName("testRowMatrix") //设定名称
//    val sc = new SparkContext(conf) //创建环境变量实例
//
//    /**
//     * 1. 分布式行矩阵
//     * RowMatrix 是一个面向行的分布式矩阵，例如，特征向量的集合。 它由其行的 RDD 支持，其中每一行都是一个本地向量，可以理解为每一行的向量内容都可以单独取出来进行操作。
//     *
//     * 可以从 RDD[Vector] 实例创建 RowMatrix， 然后可以计算它的列汇总统计和分解。 QR 分解的形式为 A = QR，其中 Q 是正交矩阵，R 是上三角矩阵。
//     */
//    def rowMatrixTest(sc: SparkContext): Unit = {
//      val rdd = sc.textFile("./a.txt") //创建RDD文件路径
//        .map(_.split(' ') //按“ ”分割
//          .map(_.toDouble)) //转成Double类型
//        .map(line => Vectors.dense(line)) //转成Vector格式
//
//      println(rdd.collect().mkString("Array(", ", ", ")")) // Array([1.0,2.0,3.0], [4.0,5.0,6.0], [7.0,8.0,9.0])
//
//      val rm = new RowMatrix(rdd) //读入行矩阵
//      println(rm.numRows()) //3
//      println(rm.numCols()) //3
//      val qrResult = rm.tallSkinnyQR(true) //获取
//      println(qrResult)
//
//      /**
//       * 结果如下：
//       * QRDecomposition(org.apache.spark.mllib.linalg.distributed.RowMatrix@49038f97,-8.124038404635959  -9.601136296387955  -11.078234188139948
//       * 0.0                 0.9045340337332926  1.809068067466585
//       * 0.0                 0.0                 -8.881784197001252E-16  )
//       */
//
//      //2. 计算每列之间的相似度，采用抽样的方法进行计算
//      val matrix: CoordinateMatrix = rm.columnSimilarities(0.5)
//      println(matrix) //结果如下： org.apache.spark.mllib.linalg.distributed.CoordinateMatrix@11abd6c
//
//
//      //3. 计算每列之间的相似度
//      val matrix1: CoordinateMatrix = rm.columnSimilarities()
//      println(matrix1.entries.collect().mkString("Array(", ", ", ")"))
//      //结果如下：Array(MatrixEntry(0,1,0.9955914553877286), MatrixEntry(1,2,0.9976931918526479), MatrixEntry(0,2,0.9869275424396535))
//
//      //4. 计算每列的汇总统计
//      val summary: MultivariateStatisticalSummary = rm.computeColumnSummaryStatistics()
//      println(summary.max) //结果如下： [7.0,8.0,9.0]
//      println(summary.min) // 结果如下：[1.0,2.0,3.0]
//      println(summary.mean) //结果如下： [4.0,5.0,6.0]
//
//      //5. 计算每列之间的协方差，生成协方差矩阵
//      val covarianceMatrix: Matrix = rm.computeCovariance()
//      println(covarianceMatrix)
//
//      /**
//       * 结果如下：
//       *
//       * 9.0  9.0  9.0
//       * 9.0  9.0  9.0
//       * 9.0  9.0  9.0
//       */
//
//      // 6. 计算格拉姆矩阵
//      val gramianMatrix = rm.computeGramianMatrix()
//      println(gramianMatrix)
//
//      //7. 主成分分析计算
//      val principalComponents = rm.computePrincipalComponents(3)
//      println(principalComponents)
//
//
//      // 8. 计算矩阵的奇异值分解
//      val svd = rm.computeSVD(4, computeU = true)
//      val u = svd.U
//      u.rows.foreach(println)
//      println(svd.s)
//      println(svd.V)
//
//      // 9. 矩阵的乘法：右乘运算,
//      //9.1 右乘矩阵
//      val matrix2 = covarianceMatrix.multiply(DenseMatrix.eye(3))
//      //9.2 右乘向量
//      val vector = covarianceMatrix.multiply(Vectors.zeros(3))
//
//
//      // 10. 矩阵转化为RDD
//      val rows: RDD[Vector] = rm.rows
//
//      // 11. 矩阵的行数和列数
//      println(covarianceMatrix.numRows)
//      println(covarianceMatrix.numCols)
//
//
//    }
//
//    //rowMatrixTest(sc)
//
//
//    import org.apache.spark.mllib.linalg.distributed.IndexedRowMatrix
//    /**
//     * 1. 行索引矩阵
//     *
//     */
//    def indexRowMatrixTest(sc: SparkContext): Unit = {
//      val row01 = IndexedRow(0, Vectors.dense(Array(1.0, 2, 3)))
//      val row02 = IndexedRow(6, Vectors.dense(Array(4.0, 5, 6)))
//      val row03 = IndexedRow(3, Vectors.dense(Array(7.0, 8, 9)))
//      val rows = sc.parallelize(Array(row01, row02, row03))
//      // 1. 创建IndexedRowMatrix
//      val indexedRowMatrix = new IndexedRowMatrix(rows, 3, 3)
//
//      // 2. 转换为其他矩阵
//      indexedRowMatrix.toBlockMatrix()
//      indexedRowMatrix.toCoordinateMatrix()
//      indexedRowMatrix.toRowMatrix()
//
//      // 3. 计算格拉姆矩阵
//      val matrix: Matrix = indexedRowMatrix.computeGramianMatrix()
//      println(matrix)
//
//      // 4. 计算矩阵的奇异值分解
//      val svd: SingularValueDecomposition[IndexedRowMatrix, Matrix] = indexedRowMatrix.computeSVD(4, computeU = true)
//      val u = svd.U
//      u.rows.foreach(println)
//      println(svd.s)
//      println(svd.V)
//
//      // 5. 矩阵的乘法：右乘矩阵, 没有右乘向量
//      val multi = indexedRowMatrix.multiply(DenseMatrix.eye(3))
//      println(multi)
//
//      // 6. 矩阵转化为RDD
//      val rdd = indexedRowMatrix.rows
//      println(rdd)
//
//      // 7. 矩阵的行数和列数
//      println(indexedRowMatrix.numRows)
//      println(indexedRowMatrix.numCols)
//
//    }
//
//    //indexRowMatrixTest(sc)
//
//
//    /**
//     * CoordinateMatrix: 坐标矩阵
//     *
//     * class CoordinateMatrix @Since("1.0.0") (
//     * @Since("1.0.0") val entries: RDD[MatrixEntry],
//     *     private var nRows: Long,private var nCols: Long) extends DistributedMatrix
//     * @param sc
//     */
//      import org.apache.spark.mllib.linalg.distributed.CoordinateMatrix
//    def coordinateMatrixTest(sc: SparkContext): Unit = {
//      val entries: RDD[MatrixEntry] = null
//      // 1. 创建IndexedRowMatrix
//      val coordinateMatrix = new CoordinateMatrix(entries, 3, 3)
//
//      // 2. 转换为其他矩阵
//      coordinateMatrix.toBlockMatrix()
//      coordinateMatrix.toRowMatrix()
//      coordinateMatrix.toIndexedRowMatrix()
//
//      //3. 矩阵的转置
//      coordinateMatrix.transpose()
//      // 6. 矩阵转化为RDD
//      println(coordinateMatrix.entries)
//
//      // 7. 矩阵的行数和列数
//      println(coordinateMatrix.numRows)
//      println(coordinateMatrix.numCols)
//
//    }
//  }
//
//}
