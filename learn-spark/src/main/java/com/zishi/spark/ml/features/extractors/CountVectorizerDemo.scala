package com.zishi.spark.ml.features.extractors

import org.apache.spark.ml.feature.{CountVectorizer, CountVectorizerModel, Word2Vec}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.{Row, SparkSession}

import scala.collection.mutable

object CountVectorizerDemo {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("CorrelationExample")
      .master("local[*]")
      .getOrCreate()


    demo01(spark)

    spark.stop()

  }

  /**
   * CountVectorizer:
   *
   * CountVectorizer and CountVectorizerModel aim to help convert a collection of text documents to vectors of token counts.
   * When an a-priori dictionary is not available, CountVectorizer can be used as an Estimator to extract the vocabulary, and generates a CountVectorizerModel.
   * The model produces sparse representations for the documents over the vocabulary, which can then be passed to other algorithms like LDA.
   *
   * During the fitting process, CountVectorizer will select the top vocabSize words ordered by term frequency across the corpus.
   * An optional parameter minDF also affects the fitting process by specifying the minimum number (or fraction if < 1.0) of documents a term must appear in to be included in the vocabulary.
   * Another optional binary toggle parameter controls the output vector. If set to true all nonzero counts are set to 1.
   * This is especially useful for discrete probabilistic models that model binary, rather than integer, counts.
   */
  def demo01(spark: SparkSession): Unit = {

    val documentDF = spark.createDataFrame(Seq(
      "Hi I heard about Spark".split(" "),
      "I wish Java could use case classes".split(" "),
      "Logistic regression models are neat".split(" ")
    ).map(Tuple1.apply)).toDF("text")

    // Learn a mapping from words to Vectors.
    val word2Vec = new Word2Vec()
      .setInputCol("text")
      .setOutputCol("result")
      .setVectorSize(3)
      .setMinCount(0)
    val model = word2Vec.fit(documentDF)

    val result = model.transform(documentDF)
    result.collect().foreach { case Row(text: mutable.ArraySeq[_], features: Vector) =>
      println(s"Text: [${text.mkString(", ")}] => \nVector: $features\n") }

    /**
     * 输出结果如下：
     * Text: [Hi, I, heard, about, Spark] =>
     * Vector: [-0.00955967416521162,0.011740577407181263,-0.029649600386619568]
     *
     * Text: [I, wish, Java, could, use, case, classes] =>
     * Vector: [-0.012418304037834916,-0.017223703317410712,0.03270330333283969]
     *
     * Text: [Logistic, regression, models, are, neat] =>
     * Vector: [-0.056402259878814226,-0.057463052123785024,0.11642010360956193]
     */
  }
}
