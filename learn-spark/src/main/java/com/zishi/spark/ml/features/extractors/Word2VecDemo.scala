package com.zishi.spark.ml.features.extractors


import org.apache.spark.ml.feature.Word2Vec
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession

import scala.collection.mutable

object Word2VecDemo {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("CorrelationExample")
      .master("local[*]")
      .getOrCreate()


    demo02(spark)

    spark.stop()

  }

  /**
   * Word2Vec: 将文本中每个词映射为向量，再平均合成为句子向量
   */
  def demo02(spark: SparkSession): Unit = {
    import spark.implicits._
    // Input data: Each row is a bag of words from a sentence or document.
    val documentDF = spark.createDataFrame(Seq(
      "Hi I heard about Spark".split(" "),
      "I wish Java could use case classes".split(" "),
      "Logistic regression models are neat".split(" ")
    ).map(Tuple1.apply)).toDF("text")
    //documentDF.show()
    /**
     * +--------------------+
     * |                text|
     * +--------------------+
     * |[Hi, I, heard, ab...|
     * |[I, wish, Java, c...|
     * |[Logistic, regres...|
     * +--------------------+
     */
    // println(documentDF.head()) // [ArraySeq(Hi, I, heard, about, Spark)]


    // Learn a mapping from words to Vectors.
    val word2Vec = new Word2Vec()
      .setInputCol("text")
      .setOutputCol("result")
      .setVectorSize(3) // 每个词向量的维度
      .setMinCount(0) // 忽略频率小于此值的词
    val model = word2Vec.fit(documentDF)

    val result = model.transform(documentDF)
    // result.collect().foreach { case Row(text: Seq[_], features: Vector) => println(s"Text: [${text.mkString(", ")}] => \nVector: $features\n") }
    // Array([ArraySeq(Hi, I, heard, about, Spark),[-0.00955967416521162,0.011740577407181263,-0.029649600386619568]],
    //       [ArraySeq(I, wish, Java, could, use, case, classes),[-0.012418304037834916,-0.017223703317410712,0.03270330333283969]])
    // result.show()
    // println(result.head()) // [ArraySeq(Hi, I, heard, about, Spark),[-0.00955967416521162,0.011740577407181263,-0.029649600386619568]]
    result.collect().foreach { case Row(text: mutable.ArraySeq[_], features: Vector) => println(s"Text: [${text.mkString(", ")}] => \nVector: $features\n") }

    // println(result.collect().mkString("Array(", ", ", ")"))
    // result.collect().foreach(x => println(x.getClass)) // class org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema

    // result.collect().foreach(x => println(x.get(0).getClass)) // class scala.collection.mutable.ArraySeq$ofRef
    // result.collect().foreach(x => println(x.get(1).getClass)) // class org.apache.spark.ml.linalg.DenseVector
//    result.collect().foreach(x => {
//      if(x.isInstanceOf[Row]) {
//        val b: Row = x
//        println(b)
//      }
//    })
  }
}
