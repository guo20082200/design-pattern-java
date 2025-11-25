package com.zishi.spark.ml.correlation

/**
 * 参考：
 * https://blog.csdn.net/guo20082200/article/details/147306924
 */
object Correlation_Demo {

  /**
   * 计算Pearson相关系数
   *
   * @param x
   * @param y
   * @return
   */
  private def calculatePearson(x: Seq[Double], y: Seq[Double]): Double = {
    require(x.size == y.size, "两组数据长度必须相同")

    val n = x.size
    val meanX = x.sum / n
    val meanY = y.sum / n

    // 计算分子 (协方差)
    val numerator = x.zip(y).map { case (xi, yi) => (xi - meanX) * (yi - meanY) }.sum

    // 计算分母 (标准差乘积)
    val denominator = math.sqrt(x.map(xi => math.pow(xi - meanX, 2)).sum) * math.sqrt(y.map(yi => math.pow(yi - meanY, 2)).sum)

    // 相关系数
    numerator / denominator
  }


  /**
   * Spearman 系数
   *
   * @param x
   * @param y
   * @return
   */
  def calculateSpearman(x: Seq[Double], y: Seq[Double]): Double = {
    require(x.size == y.size, "两组数据长度必须相同")

    val n = x.size

    // 将数据转换为排名
    def rank(data: Seq[Double]): Seq[Double] = {
      data.zipWithIndex
        .sortBy(_._1)
        .zipWithIndex
        .map { case ((_, originalIndex), rankIndex) =>
          (originalIndex, rankIndex + 1) // 排名从1开始
        }
        .sortBy(_._1) // 按原始顺序排序
        .map(_._2.toDouble)
    }

    val rankX = rank(x) // List(3.0, 4.0, 2.0, 1.0, 5.0)
    val rankY = rank(y) // List(3.0, 5.0, 1.0, 2.0, 4.0)

    // 计算排名差值
    val d = rankX.zip(rankY).map { case (rx, ry) => rx - ry }
    //println(d) //List(0.0, -1.0, 1.0, -1.0, 1.0)
    val dSquaredSum = d.map(dValue => dValue * dValue).sum

    // Spearman相关系数公式
    val spearmanCoefficient = 1 - (6 * dSquaredSum) / (n * (n * n - 1))
    spearmanCoefficient
  }

  def main(args: Array[String]): Unit = {
    //    val x = Seq(3.0, 2.0, 4.0, 5.0)
    //    val y = Seq(5.0, 4.0, 6.0, 8.0)
    //
    //    val a = x.zip(y)
    //    println(a) // List((3.0,5.0), (2.0,4.0), (4.0,6.0), (5.0,8.0))
    //
    //    val result = calculatePearson(x, y)
    //    println(s"Pearson相关系数: $result")

    val a = Seq(1, 0, 0, -2.0)
    val b = Seq(6, 7, 0, 8.0)
    val res = calculatePearson(a, b)
    println(s"Pearson相关系数: $res")


    val x = Seq(80.0, 90.0, 70.0, 60.0, 100.0)
    //println(x.zipWithIndex) // List((80.0,0), (90.0,1), (70.0,2), (60.0,3), (100.0,4))
    //println(x.zipWithIndex.sortBy(_._1)) // List((60.0,3), (70.0,2), (80.0,0), (90.0,1), (100.0,4))
    //println(x.zipWithIndex.sortBy(_._1).zipWithIndex) // List(((60.0,3),0), ((70.0,2),1), ((80.0,0),2), ((90.0,1),3), ((100.0,4),4))
    //    println(x.zipWithIndex.sortBy(_._1).zipWithIndex.map { case ((_, originalIndex), rankIndex) =>
    //      (originalIndex, rankIndex + 1) // 排名从1开始
    //    }) // List((3,1), (2,2), (0,3), (1,4), (4,5))

    //    println(x.zipWithIndex.sortBy(_._1).zipWithIndex.map { case ((_, originalIndex), rankIndex) =>
    //      (originalIndex, rankIndex + 1) //
    //    }.sortBy(_._1) // 按原始顺序排序 // List((0,3), (1,4), (2,2), (3,1), (4,5))
    //      .map(_._2.toDouble)) // List(3.0, 4.0, 2.0, 1.0, 5.0)

    val y = Seq(85.0, 95.0, 60.0, 75.0, 90.0)

    //    println(y.zipWithIndex.sortBy(_._1).zipWithIndex.map { case ((_, originalIndex), rankIndex) =>
    //        (originalIndex, rankIndex + 1) //
    //      }.sortBy(_._1)
    //      .map(_._2.toDouble)) // List(3.0, 5.0, 1.0, 2.0, 4.0)

    val result = calculateSpearman(x, y)
    println(s"Spearman相关系数: $result")
  }
}
