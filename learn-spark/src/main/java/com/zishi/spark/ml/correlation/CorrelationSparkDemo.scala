package com.zishi.spark.ml.correlation

// $example on$

import org.apache.spark.ml.linalg.{Matrix, Vectors, Vector}
import org.apache.spark.ml.stat.{ChiSquareTest, Correlation}
import org.apache.spark.sql.Row
// $example off$
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.stat.Summarizer

/**
 * spark中相关系数的计算
 */
object CorrelationSparkDemo {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("CorrelationExample")
      .master("local[*]")
      .getOrCreate()


    // test02(spark)
    test03(spark)


    spark.stop()
  }

  def test01(spark: SparkSession): Unit = {
    import spark.implicits._
    // $example on$
    //    val data = Seq(
    //      Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))),
    //      Vectors.dense(4.0, 5.0, 0.0, 3.0),
    //      Vectors.dense(6.0, 7.0, 0.0, 8.0),
    //      Vectors.sparse(4, Seq((0, 9.0), (3, 1.0)))
    //    )

    val data = Seq(
      Vectors.dense(1.0, 0.0, 0.0, -2.0),
      Vectors.dense(4.0, 5.0, 0.0, 3.0),
      Vectors.dense(6.0, 7.0, 0.0, 8.0),
      Vectors.dense(9.0, 0.0, 0.0, 1.0)
    )

    val d = data.map(Tuple1.apply)
    println(d.getClass)
    /**
     * DataFrame 需要指定schema
     */
    val df = data.map(Tuple1.apply).toDF("features")

    /**
     * 操作机器学习库都是通过 DataFrame完成
     * df的结果如下：
     *
     * +--------------------+
     * |            features|
     * +--------------------+
     * |(4,[0,3],[1.0,-2.0])|
     * |   [4.0,5.0,0.0,3.0]|
     * |   [6.0,7.0,0.0,8.0]|
     * | (4,[0,3],[9.0,1.0])|
     * +--------------------+
     */
    df.show()


    /**
     * 通过df.select("features") 操作
     * df.head 取第一个元素
     */
    //这里计算的是相关系数矩阵，为什么计算结果和python程序不一样呢？
    val Row(coeff1: Matrix) = Correlation.corr(df, "features").head

    println(s"Pearson correlation matrix:\n $coeff1")

    // 计算 Pearson 相关系数矩阵
    val matrix: Matrix = Correlation.corr(df, "features").head.getAs[Matrix](0)
    println(s"相关系数矩阵: \n$matrix")

    val Row(coeff2: Matrix) = Correlation.corr(df, "features", "spearman").head
    println(s"Spearman correlation matrix:\n $coeff2")
    // $example off$
  }

  def test02(spark: SparkSession): Unit = {
    val data = Seq(
      (0.0, Vectors.dense(0.5, 10.0)),
      (0.0, Vectors.dense(1.5, 20.0)),
      (1.0, Vectors.dense(1.5, 30.0)),
      (0.0, Vectors.dense(3.5, 30.0)),
      (0.0, Vectors.dense(3.5, 40.0)),
      (1.0, Vectors.dense(3.5, 40.0))
    )
    import spark.implicits._
    val df = data.toDF("label", "features")

    val chi = ChiSquareTest.test(df, "features", "label").head
    println(s"pValues = ${chi.getAs[Vector](0)}")
    println(s"degreesOfFreedom ${chi.getSeq[Int](1).mkString("[", ",", "]")}")
    println(s"statistics ${chi.getAs[Vector](2)}")

  }
  def test03(spark: SparkSession): Unit = {


    val data = Seq(
      (Vectors.dense(2.0, 3.0, 5.0), 1.0),
      (Vectors.dense(4.0, 6.0, 7.0), 2.0)
    )

    import spark.implicits._
    val df = data.toDF("features", "weight")

    /**
     * +-------------+------+
     * |     features|weight|
     * +-------------+------+
     * |[2.0,3.0,5.0]|   1.0|
     * |[4.0,6.0,7.0]|   2.0|
     * +-------------+------+
     */

    /**
     * Summarizer.metrics 是一个函数，它允许你指定想要计算的统计量。在这里，我们要求计算 均值（mean）和 方差（variance）。这个函数返回一个 Summarizer 对象
     * summary 是 Summarizer 类中的一个方法，用来对指定的列（这里是 features）进行计算。
     * $"features" 是 Spark SQL 中用于引用列的语法（也叫做列表达式）
     * .as("summary") 是为计算结果指定一个别名，将计算出的结果列命名为 summary，使其更容易理解和引用。
     * Summarizer.metrics("mean", "variance").summary($"features", $"weight").as("summary") 这里返回的Column对象，放在select函数里面
     */
    val (meanVal, varianceVal) = df.select(Summarizer.metrics("mean", "variance").summary($"features", $"weight").as("summary"))
      .select("summary.mean", "summary.variance")
      .as[(Vector, Vector)].first()

    println(s"with weight: mean = ${meanVal}, variance = ${varianceVal}")

    val (meanVal2, varianceVal2) = df.select(Summarizer.mean($"features"), Summarizer.variance($"features")).as[(Vector, Vector)].first()

    println(s"without weight: mean = ${meanVal2}, varianceVal2 = ${varianceVal2}")
  }

}
