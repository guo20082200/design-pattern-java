package com.zishi.spark.ml.correlation


import org.apache.spark.ml.linalg.{Matrix, Vectors}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.sql.{Row, SparkSession}

/**
 * spark中相关系数的计算
 */
object CorrelationSparkDemo {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder
      .master("spark://172.16.23.247:27077")
      .appName("CorrelationExample")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .getOrCreate()
    println(spark.sparkContext)
    import spark.implicits._

    val data = Seq(
      Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))),
      Vectors.dense(4.0, 5.0, 0.0, 3.0),
      Vectors.dense(6.0, 7.0, 0.0, 8.0),
      Vectors.sparse(4, Seq((0, 9.0), (3, 1.0)))
    )

    println(data) // List((4,[0,3],[1.0,-2.0]), [4.0,5.0,0.0,3.0], [6.0,7.0,0.0,8.0], (4,[0,3],[9.0,1.0]))
    println(data.map(Tuple1.apply)) // List(((4,[0,3],[1.0,-2.0])), ([4.0,5.0,0.0,3.0]), ([6.0,7.0,0.0,8.0]), ((4,[0,3],[9.0,1.0])))

    //val d = List(((4,[0,3],[1.0,-2.0])), ([4.0,5.0,0.0,3.0]), ([6.0,7.0,0.0,8.0]), ((4,[0,3],[9.0,1.0])))




    val df = data.map(Tuple1.apply).toDF("features")
    df.show()

//    val Row(coeff1: Matrix) = Correlation.corr(df, "features").head
//    println(s"Pearson correlation matrix:\n $coeff1")
//
//    val Row(coeff2: Matrix) = Correlation.corr(df, "features", "spearman").head
//    println(s"Spearman correlation matrix:\n $coeff2")
  }
}
