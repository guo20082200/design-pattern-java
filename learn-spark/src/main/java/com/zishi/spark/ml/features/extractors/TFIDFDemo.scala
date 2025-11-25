package com.zishi.spark.ml.features.extractors

import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer, Word2Vec}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.{Row, SparkSession}

/**
 * 特征提取
 */
object TFIDFDemo {


  /**
   * TF and IDF， 这里分开计算
   * TF: HashingTF 和 CountVectorizer 可以用来产生  term frequency vectors
   * | 对比项        | CountVectorizer | HashingTF     |
   * | ----------  | --------------- | ------------- |
   * | 词典建立     | 需要构建词典          | 不需要词典         |
   * | 特征维度     | 随数据增加自动增长       | 固定维度（如 2^20）  |
   * | 内存使用     | 占用较高            | 占用较低          |
   * | 可解释性     | 强（词与特征一一对应）     | 弱（哈希不可逆）      |
   * | 哈希冲突     | 无               | 有可能冲突（但通常可接受） |
   * | 适合场景     | 小到中等规模文本        | 大规模文本、流式数据    |
   * | 模型可部署性 | 高（向量可重现）        | 一定程度上不易调试     |
   *
   *
   * IDF: 是一个 Estimator，拟合dataset并生成一个模型 IDFModel，IDFModel接收特征向量（一般是通过HashingTF or CountVectorizer产生的）并表征每一个特征
   * 直观地，它来降低一个在语料库出现频率高的特征的权重
   *
   * @param spark
   */
  def demo01(spark: SparkSession): Unit = {

    // $example on$
    val sentenceData = spark.createDataFrame(Seq(
      (0.0, "Hi I heard about Spark"),
      (0.0, "I wish Java could use case classes"),
      (1.0, "Logistic regression models are neat")
    )).toDF("label", "sentence")


    //println(sentenceData.schema) // StructType(StructField(label,DoubleType,false),StructField(sentence,StringType,true))
    // 分词器，将词分割为数组
    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val wordsData = tokenizer.transform(sentenceData)
    //wordsData.show()
    /**
     * +-----+--------------------+--------------------+
     * |label|            sentence|               words|
     * +-----+--------------------+--------------------+
     * |  0.0|Hi I heard about ...|[hi, i, heard, ab...|
     * |  0.0|I wish Java could...|[i, wish, java, c...|
     * |  1.0|Logistic regressi...|[logistic, regres...|
     * +-----+--------------------+--------------------+
     */
    //println(wordsData.head) // [0.0,Hi I heard about Spark,ArraySeq(hi, i, heard, about, spark)]

    /**
     * 通过哈希函数将词语映射到固定维度的特征向量空间，不需要存储词典
     * setInputCol： 设置输入的列名
     * setOutputCol：设置输出的列名，输出为稀疏矩阵
     * setNumFeatures： 设置特征的维度，即所有词都被映射到长度为 64（2^8） 的向量中。维度越高，哈希冲突概率越小，但也会增加内存消耗。 生产环境中常设为 2^18（262144） 或更高。
     */
    val hashingTF = new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(512)
    // 计算 term frequency
    val featurizedData = hashingTF.transform(wordsData)
    // featurizedData.show
    /**
     * +-----+--------------------+--------------------+--------------------+
     * |label|            sentence|               words|         rawFeatures|
     * +-----+--------------------+--------------------+--------------------+
     * |  0.0|Hi I heard about ...|[hi, i, heard, ab...|(20,[6,8,13,16],[...|
     * |  0.0|I wish Java could...|[i, wish, java, c...|(20,[0,2,7,13,15,...|
     * |  1.0|Logistic regressi...|[logistic, regres...|(20,[3,4,6,11,19]...|
     * +-----+--------------------+--------------------+--------------------+
     */
    // println(featurizedData.head) // [0.0,Hi I heard about Spark,ArraySeq(hi, i, heard, about, spark),(20,[6,8,13,16],[1.0,1.0,1.0,2.0])]

    /**
     * ArraySeq(hi, i, heard, about, spark),(64,[12,16,28,33,54],[1.0,1.0,1.0,1.0,1.0])
     * hi - 第12位数，词频是 1
     * i - 第16位数，词频是 1
     * heard - 第28位数，词频是 1
     * about - 第33位数，词频是 1
     * spark - 第54位数，词频是 1
     */
    //println(featurizedData.take(2).mkString("Array(", ", ", ")"))
    // Array([0.0,Hi I heard about Spark,ArraySeq(hi, i, heard, about, spark),(64,[12,16,28,33,54],[1.0,1.0,1.0,1.0,1.0])],
    //       [0.0,I wish Java could use case classes,ArraySeq(i, wish, java, could, use, case, classes),(64,[28,29,43,47,48,54,63],[1.0,1.0,1.0,1.0,1.0,1.0,1.0])])
    // 计算idf
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")

    // TODO: 先拟合，拟合的结果是产生一个模型， 模型就是一个转换器，该转换器作用就是读取dataframe数据，并predicted labels
    val idfModel = idf.fit(featurizedData)
    // println(idfModel.getClass) // class org.apache.spark.ml.feature.IDFModel

    // TODO:再转换
    val rescaledData = idfModel.transform(featurizedData)
    println(rescaledData.select("label", "features").head(2).mkString("Array(", ", ", ")"))

  }



  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("CorrelationExample")
      .master("local[*]")
      .getOrCreate()


    //demo01(spark)

    spark.stop()

  }
}
