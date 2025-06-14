package com.zishi.spark.ml.features.transformers

import org.apache.spark.ml.attribute.Attribute
import org.apache.spark.ml.feature._
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, udf}

/**
 * Feature Transformers
 */
object TokenizerDemo {


  /**
   * Tokenizer: 分词器
   *
   * @param spark
   */
  def tokenizerDemo01(spark: SparkSession): Unit = {
    // $example on$
    val sentenceDataFrame = spark.createDataFrame(Seq(
      (0, "Hi I heard about Spark"),
      (1, "I wish Java could use case classes"),
      (2, "Logistic,regression,models,are,neat")
    )).toDF("id", "sentence")

    // 默认按照空格来切分
    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")

    // 高级分词器，按照正则表达式来切割
    val regexTokenizer = new RegexTokenizer()
      .setInputCol("sentence")
      .setOutputCol("words")
      .setPattern("\\W") // alternatively .setPattern("\\w+").setGaps(false)

    // 这里使用了udf函数
    val countTokens = udf { (words: Seq[String]) => words.length }

    val tokenized = tokenizer.transform(sentenceDataFrame)

    tokenized.select("sentence", "words")
      .withColumn("tokens", countTokens(col("words"))).show(false)

    val regexTokenized = regexTokenizer.transform(sentenceDataFrame)

    regexTokenized.select("sentence", "words")
      .withColumn("tokens", countTokens(col("words"))).show(false)

  }


  /**
   * StopWordsRemover
   *
   * @param spark
   */
  def stopWordsRemoverDemo01(spark: SparkSession): Unit = {
    val remover = new StopWordsRemover()
      .setInputCol("raw")
      .setOutputCol("filtered")

    val dataSet = spark.createDataFrame(Seq(
      (0, Seq("I", "saw", "the", "red", "balloon")),
      (1, Seq("Mary", "had", "a", "little", "lamb"))
    )).toDF("id", "raw")

    remover.transform(dataSet).show(false)

    /**
     * +---+----------------------------+--------------------+
     * |id |raw                         |filtered            |
     * +---+----------------------------+--------------------+
     * |0  |[I, saw, the, red, balloon] |[saw, red, balloon] |
     * |1  |[Mary, had, a, little, lamb]|[Mary, little, lamb]|
     * +---+----------------------------+--------------------+
     */
  }

  /**
   * NGram： 将文本里面的内容按照字节进行大小为N的滑动窗口操作，形成了长度是N的字节片段序列。
   *
   * @param args
   */
  def nGramDemo01(spark: SparkSession): Unit = {
    val wordDataFrame = spark.createDataFrame(Seq(
      (0, Array("Hi", "I", "heard", "about", "Spark")),
      (1, Array("I", "wish", "Java", "could", "use", "case", "classes")),
      (2, Array("Logistic", "regression", "models", "are", "neat"))
    )).toDF("id", "words")

    val ngram = new NGram().setN(3).setInputCol("words").setOutputCol("ngrams")

    val ngramDataFrame = ngram.transform(wordDataFrame)
    ngramDataFrame.select("ngrams").show(false)

    /**
     * 输出结果如下：
     * +--------------------------------------------------------------------------------+
     * |ngrams                                                                          |
     * +--------------------------------------------------------------------------------+
     * |[Hi I heard, I heard about, heard about Spark]                                  |
     * |[I wish Java, wish Java could, Java could use, could use case, use case classes]|
     * |[Logistic regression models, regression models are, models are neat]            |
     * +--------------------------------------------------------------------------------+
     */
  }

  /**
   * Binarizer: 将数值处理为 0/1， 特征大于阈值就是1.0，特征小于等于阈值就是 0.0
   * 输入可以是数值vector或者double类型的数字
   *
   * @param spark
   */
  def binarizerDemo01(spark: SparkSession): Unit = {
    val data = Array((0, 0.1), (1, 0.8), (2, 0.2))
    val dataFrame = spark.createDataFrame(data).toDF("id", "feature")

    val binarizer: Binarizer = new Binarizer()
      .setInputCol("feature") // 接收的input列数据
      .setOutputCol("binarized_feature") // 输出列
      .setThreshold(0.5) // 设置阈值

    val binarizedDataFrame = binarizer.transform(dataFrame)

    println(s"Binarizer output with Threshold = ${binarizer.getThreshold}")
    binarizedDataFrame.show()

    /**
     * +---+-------+-----------------+
     * | id|feature|binarized_feature|
     * +---+-------+-----------------+
     * |  0|    0.1|              0.0|
     * |  1|    0.8|              1.0|
     * |  2|    0.2|              0.0|
     * +---+-------+-----------------+
     */
  }

  /**
   * PCA: 主成分分析（Principal Component Analysis, PCA）
   * 是一种广泛使用的降维技术，旨在通过将数据投影到低维空间来减少变量数量，同时尽可能保留数据的主要信息
   *
   * PCA 是一个统计的过程，使用一个正交变换来转换一组可能相关的变量的观测量为一组线性不相关变量的值（称之为主成分 PC）
   * PCA 这个类会训练一个模型来使得使用主成分 将向量投影到一个低维的空间上
   * 下面的例子是：投影一个5维的特征向量到3维的主成分中
   *
   * @param spark : SparkSession
   */
  def pcaDemo01(spark: SparkSession): Unit = {
    val data = Array(
      Vectors.sparse(5, Seq((1, 1.0), (3, 7.0))),
      Vectors.dense(2.0, 0.0, 3.0, 4.0, 5.0),
      Vectors.dense(4.0, 0.0, 0.0, 6.0, 7.0)
    )
    val df = spark.createDataFrame(data.map(Tuple1.apply)).toDF("features")

    val pca = new PCA()
      .setInputCol("features")
      .setOutputCol("pcaFeatures")
      .setK(3)
      .fit(df)

    val result = pca.transform(df).select("pcaFeatures")
    result.show(false)

    /**
     *
     * +------------------------------------------------------------+
     * |pcaFeatures                                                 |
     * +------------------------------------------------------------+
     * |[1.6485728230883814,-4.0132827005162985,-1.0091435193998504]|
     * |[-4.645104331781533,-1.1167972663619048,-1.0091435193998504]|
     * |[-6.428880535676488,-5.337951427775359,-1.0091435193998508] |
     * +------------------------------------------------------------+
     */

  }


  /**
   * PolynomialExpansion:
   * degree = 2, 特征向量(x, y)为例，如果我们想用2次展开它，那么我们得到(x, x * x, y, x * y, y * y)
   *
   * degree = 3, 特征向量(x, y)为例，如果我们想用3次展开它，那么我们得到(x, y, x*x, y*y, x * y, x *x*y , x*y*y，x*x*x， y*y*y)
   *
   * 特征向量(x, y, z)，如果我们想用2次展开它，那么我们得到(x, y, z, x*x, y*y, z*z, x * y, x *z , y * z)
   *
   * @param spark : SparkSession
   */
  def polynomialExpansionDemo(spark: SparkSession): Unit = {
    val data = Array(
      Vectors.dense(2.0, 2.0),
      Vectors.dense(1.0, 2.0),
      Vectors.dense(3.0, -1.0)
    )
    val df = spark.createDataFrame(data.map(Tuple1.apply)).toDF("features")

    val polyExpansion = new PolynomialExpansion()
      .setInputCol("features")
      .setOutputCol("polyFeatures")
      .setDegree(2)

    val polyDF = polyExpansion.transform(df)
    polyDF.show(false)

    /**
     * degree = 2, 特征向量(x, y)为例，如果我们想用2次展开它，那么我们得到(x, x * x, y, x * y, y * y)
     * +----------+-----------------------+
     * |features  |polyFeatures           |
     * +----------+-----------------------+
     * |[2.0,2.0] |[2.0,4.0,2.0,4.0,4.0]  |
     * |[1.0,2.0] |[1.0,1.0,2.0,2.0,4.0]  |
     * |[3.0,-1.0]|[3.0,9.0,-1.0,-3.0,1.0]|
     * +----------+-----------------------+
     */

    val polyExpansion2 = new PolynomialExpansion()
      .setInputCol("features")
      .setOutputCol("polyFeatures")
      .setDegree(3)

    val polyDF2 = polyExpansion2.transform(df)
    polyDF2.show(false)

    /**
     * -- degree = 3, 特征向量(x, y)为例，如果我们想用3次展开它，那么我们得到(x, y, x*x, y*y, x * y, x *x*y , x*y*y，x*x*x， y*y*y)  TODO: 感觉不对
     * degree = 3, 特征向量(x, y)为例，如果我们想用3次展开它，那么我们得到(x, x*x, x*x*x, y, x * y, x*x*y , y*y，x*y*y， y*y*y)
     * +----------+------------------------------------------+
     * |features  |polyFeatures                              |
     * +----------+------------------------------------------+
     * |[2.0,2.0] |[2.0,4.0,8.0,2.0,4.0,8.0,4.0,8.0,8.0]     |
     * |[1.0,2.0] |[1.0,1.0,1.0,2.0,2.0,2.0,4.0,4.0,8.0]     |
     * |[3.0,-1.0]|[3.0,9.0,27.0,-1.0,-3.0,-9.0,1.0,3.0,-1.0]|
     * +----------+------------------------------------------+
     */


    val data3 = Array(
      Vectors.dense(2.0, 2.0, 3.0),
      Vectors.dense(1.0, 2.0, 3.0),
      Vectors.dense(3.0, -1.0, 2.0)
    )
    val df3 = spark.createDataFrame(data3.map(Tuple1.apply)).toDF("features")
    val polyExpansion3 = new PolynomialExpansion()
      .setInputCol("features")
      .setOutputCol("polyFeatures")
      .setDegree(2)

    val polyDF3 = polyExpansion3.transform(df3)
    polyDF3.show(false)

    /**
     * 特征向量(x, y, z)，如果我们想用2次展开它，那么我们得到(x, x*x, y, x*y, y*y, z, x * z, y *z , z * z)
     * +--------------+----------------------------------------+
     * |features      |polyFeatures                            |
     * +--------------+----------------------------------------+
     * |[2.0,2.0,3.0] |[2.0,4.0,2.0,4.0,4.0,3.0,6.0,6.0,9.0]   |
     * |[1.0,2.0,3.0] |[1.0,1.0,2.0,2.0,4.0,3.0,3.0,6.0,9.0]   |
     * |[3.0,-1.0,2.0]|[3.0,9.0,-1.0,-3.0,1.0,2.0,6.0,-2.0,4.0]|
     * +--------------+----------------------------------------+
     */
  }

  /**
   * Discrete Cosine Transform (DCT) : 时域转频域（和傅里叶变换有区别）
   *
   * todo: 一维和二维如何实现，DCT还有一些变形，还存在逆运算
   *
   * @param spark : SparkSession
   */
  def dctDemo(spark: SparkSession): Unit = {
    //    val data = Seq(
    //      Vectors.dense(0.0, 1.0, -2.0, 3.0),
    //      Vectors.dense(-1.0, 2.0, 4.0, -7.0),
    //      Vectors.dense(14.0, -2.0, -5.0, 1.0))

    val data = Seq(
      Vectors.dense(1.0, 3.0),
      Vectors.dense(2.0, 4))

    val df = spark.createDataFrame(data.map(Tuple1.apply)).toDF("features")

    val dct = new DCT()
      .setInputCol("features")
      .setOutputCol("featuresDCT")
      .setInverse(false)

    val dctDf = dct.transform(df)
    dctDf.select("features", "featuresDCT").show(false)

    /**
     * +---------+---------------------------------------+
     * |features |featuresDCT                            |
     * +---------+---------------------------------------+
     * |[1.0,3.0]|[2.82842712474619,-1.4142135623730951] |
     * |[2.0,4.0]|[4.242640687119285,-1.4142135623730951]|
     * +---------+---------------------------------------+
     */
  }


  /**
   * Spark的机器学习处理过程中，经常需要把标签数据（一般是字符串）转化成整数索引，
   * * 而在计算结束又需要把整数索引还原为标签。
   * * 这就涉及到几个转换器：StringIndexer、 IndexToString，OneHotEncoder，以及针对类别特征的索引VectorIndexer。
   *
   * StringIndexer : StringIndexer是指把一组字符型标签编码成一组标签索引，索引的范围为0到标签数量，索引构建的顺序为标签的频率，优先编码频率较大的标签，所以出现频率最高的标签为0号
   *
   * @param spark : SparkSession
   */
  def stringIndexerDemo(spark: SparkSession): Unit = {
    val df = spark.createDataFrame(
      Seq((0, "a"), (1, "b"), (2, "c"), (3, "a"), (4, "a"), (5, "c"))
    ).toDF("id", "category")

    val indexer = new StringIndexer()
      .setInputCol("category")
      .setOutputCol("categoryIndex")

    val indexed = indexer.fit(df).transform(df)
    indexed.show()

    /**
     * +---+--------+-------------+
     * | id|category|categoryIndex|
     * +---+--------+-------------+
     * |  0|       a|          0.0|
     * |  1|       b|          2.0|
     * |  2|       c|          1.0|
     * |  3|       a|          0.0|
     * |  4|       a|          0.0|
     * |  5|       c|          1.0|
     * +---+--------+-------------+
     */
  }

  /**
   * IndexToString:
   * 对称的，IndexToString的作用是把标签索引的一列重新映射回原有的字符型标签。
   * 一般都是和StringIndexer配合，先用StringIndexer转化成标签索引，进行模型训练，然后在预测标签的时候再把标签索引转化成原有的字符标签。
   * 当然，也允许你使用自己提供的标签。
   * @param spark
   */
  def indexToStringDemo(spark: SparkSession): Unit = {
    val df = spark.createDataFrame(Seq(
      (0, "a"),
      (1, "b"),
      (2, "c"),
      (3, "a"),
      (4, "a"),
      (5, "c")
    )).toDF("id", "category")

    val indexer = new StringIndexer()
      .setInputCol("category")
      .setOutputCol("categoryIndex")
      .fit(df)
    val indexed = indexer.transform(df)

    println(s"Transformed string column '${indexer.getInputCol}' " +
      s"to indexed column '${indexer.getOutputCol}'")
    indexed.show()

    val inputColSchema = indexed.schema(indexer.getOutputCol)
    println(s"StringIndexer will store labels in output column metadata: " +
      s"${Attribute.fromStructField(inputColSchema).toString}\n")

    val converter = new IndexToString()
      .setInputCol("categoryIndex")
      .setOutputCol("originalCategory")

    val converted = converter.transform(indexed)

    println(s"Transformed indexed column '${converter.getInputCol}' back to original string " +
      s"column '${converter.getOutputCol}' using labels in metadata")
    converted.select("id", "categoryIndex", "originalCategory").show()

    /**
     * Transformed indexed column 'categoryIndex' back to original string column 'originalCategory' using labels in metadata
     * +---+-------------+----------------+
     * | id|categoryIndex|originalCategory|
     * +---+-------------+----------------+
     * |  0|          0.0|               a|
     * |  1|          2.0|               b|
     * |  2|          1.0|               c|
     * |  3|          0.0|               a|
     * |  4|          0.0|               a|
     * |  5|          1.0|               c|
     * +---+-------------+----------------+
     */

  }

  /**
   * OneHotEncoder:独热编码是指把一列标签索引映射成一列二进制数组，且最多的时候只有一位有效。这种编码适合一些期望类别特征为连续特征的算法，比如说逻辑斯蒂回归。
   * @param spark
   */
  def oneHotEncoderDemo(spark: SparkSession): Unit = {
    val df = spark.createDataFrame(Seq(
      (0.0, 1.0),
      (1.0, 0.0),
      (2.0, 1.0),
      (0.0, 2.0),
      (0.0, 1.0),
      (2.0, 0.0)
    )).toDF("categoryIndex1", "categoryIndex2")

    val encoder = new OneHotEncoder()
      .setInputCols(Array("categoryIndex1", "categoryIndex2"))
      .setOutputCols(Array("categoryVec1", "categoryVec2"))
    val model = encoder.fit(df)
    val encoded = model.transform(df)
    encoded.show()
  }

  /**
   * VectorIndexer:
   * @param spark
   */
  def vectorIndexerDemo(spark: SparkSession): Unit = {
    val data = spark.read.format("libsvm").load("learn-spark/data/mllib/sample_libsvm_data.txt")

    //println(data.head())
    // [0.0,(692,[127,242,6, ...... , 657],[51.0,......,37.0])]
    //data.show()
    /**
     * +-----+--------------------+
     * |label|            features|
     * +-----+--------------------+
     * |  0.0|(692,[127,128,129...|
     * |  1.0|(692,[158,159,160...|
     * |  1.0|(692,[124,125,126...|
     * |  1.0|(692,[152,153,154...|
     * |  1.0|(692,[151,152,153...|
     */


    val indexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexed")
      .setMaxCategories(10)

    val indexerModel = indexer.fit(data)

    // println(indexerModel.categoryMaps)
    val categoricalFeatures: Set[Int] = indexerModel.categoryMaps.keys.toSet
    println(s"Chose ${categoricalFeatures.size} " + s"categorical features: ${categoricalFeatures.mkString(", ")}")

    // Create new column "indexed" with categorical values transformed to indices
    val indexedData = indexerModel.transform(data)
    indexedData.show()
  }

  def main01(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("CorrelationExample")
      .master("local[*]")
      .getOrCreate()

    // tokenizerDemo01(spark)
    // stopWordsRemoverDemo01(spark)
    // nGramDemo01(spark)
    // binarizerDemo01(spark)
    // pcaDemo01(spark)
    // polynomialExpansionDemo(spark)
    // dctDemo(spark)
    // stringIndexerDemo(spark)
    // indexToStringDemo(spark)
    // oneHotEncoderDemo(spark)
    vectorIndexerDemo(spark)

    spark.stop()

  }

  // =====================================================================

  /**
   * Interaction: 向量的各个元素的乘积
   * @param spark
   */
  def interactionDemo(spark: SparkSession): Unit = {
    val df = spark.createDataFrame(Seq(
      (1, 1, 2, 3, 8, 4, 5),
      (2, 4, 3, 8, 7, 9, 8),
      (3, 6, 1, 9, 2, 3, 6),
      (4, 10, 8, 6, 9, 4, 5),
      (5, 9, 2, 7, 10, 7, 3),
      (6, 1, 1, 4, 2, 8, 4)
    )).toDF("id1", "id2", "id3", "id4", "id5", "id6", "id7")

    val assembler1 = new VectorAssembler().
      setInputCols(Array("id2", "id3", "id4")).
      setOutputCol("vec1")

    val assembled1 = assembler1.transform(df)

    val assembler2 = new VectorAssembler().
      setInputCols(Array("id5", "id6", "id7")).
      setOutputCol("vec2")

    val assembled2 = assembler2.transform(assembled1).select("id1", "vec1", "vec2")

    val interaction = new Interaction()
      .setInputCols(Array("id1", "vec1", "vec2"))
      .setOutputCol("interactedCol")

    val interacted = interaction.transform(assembled2)

    interacted.show(truncate = false)

    /**
     * +---+--------------+--------------+------------------------------------------------------+
     * |id1|vec1          |vec2          |interactedCol                                         |
     * +---+--------------+--------------+------------------------------------------------------+
     * |1  |[1.0,2.0,3.0] |[8.0,4.0,5.0] |[8.0,4.0,5.0,16.0,8.0,10.0,24.0,12.0,15.0]            |
     * |2  |[4.0,3.0,8.0] |[7.0,9.0,8.0] |[56.0,72.0,64.0,42.0,54.0,48.0,112.0,144.0,128.0]     |
     * |3  |[6.0,1.0,9.0] |[2.0,3.0,6.0] |[36.0,54.0,108.0,6.0,9.0,18.0,54.0,81.0,162.0]        |
     * |4  |[10.0,8.0,6.0]|[9.0,4.0,5.0] |[360.0,160.0,200.0,288.0,128.0,160.0,216.0,96.0,120.0]|
     * |5  |[9.0,2.0,7.0] |[10.0,7.0,3.0]|[450.0,315.0,135.0,100.0,70.0,30.0,350.0,245.0,105.0] |
     * |6  |[1.0,1.0,4.0] |[2.0,8.0,4.0] |[12.0,48.0,24.0,12.0,48.0,24.0,48.0,192.0,96.0]       |
     * +---+--------------+--------------+------------------------------------------------------+
     */
  }

  /**
   * Normalizer：Normalizer 是一个转换器， 用来转换一个向量行的数据集，将每一个Vector归一化为单元的norm。
   * 输入参数为p，表征使用p-norm来做归一化，默认p = 2.
   * 该归一化可以标准化输入数据并且提升学习算法的行为
   * @param spark
   */
  def normalizerDemo(spark: SparkSession): Unit = {
    val dataFrame = spark.createDataFrame(Seq(
      (0, Vectors.dense(1.0, 0.5, -1.0)),
      (1, Vectors.dense(2.0, 1.0, 1.0)),
      (2, Vectors.dense(4.0, 10.0, 2.0))
    )).toDF("id", "features")

    // Normalize each Vector using $L^1$ norm.
    val normalizer = new Normalizer()
      .setInputCol("features")
      .setOutputCol("normFeatures")
      .setP(1.0)

    val l1NormData = normalizer.transform(dataFrame)
    println("Normalized using L^1 norm")
    l1NormData.show() // 向量各个元素的和为1

    // Normalize each Vector using $L^\infty$ norm.
    val lInfNormData = normalizer.transform(dataFrame, normalizer.p -> Double.PositiveInfinity)
    println("Normalized using L^inf norm")
    lInfNormData.show()
  }

  /**
   * StandardScaler: 用来转换行向量, 转换每一个特征都有单位标准差 并/或 0平均值
   * 有两个参数：
   * withStd: 默认为True ，将数据  Scales 到单位标准差
   * withMean: 默认为 False，在scaling数据之前将数据中心化（输出dense向量，所以当输入为sparse向量的时候应当小心）
   * @param spark
   */
  def standardScalerDemo(spark: SparkSession): Unit = {

    val dataFrame = spark.createDataFrame(Seq(
      (0, Vectors.dense(1.0, 2.0, 3.0)),
      (1, Vectors.dense(4.0, 5.0, 6.0)),
      (2, Vectors.dense(7.0, 8.0, 9.0))
    )).toDF("id", "features")

    val scaler = new StandardScaler()
      .setInputCol("features")
      .setOutputCol("scaledFeatures")
      .setWithStd(true)
      .setWithMean(false)

    // Compute summary statistics by fitting the StandardScaler.
    val scalerModel = scaler.fit(dataFrame)

    // Normalize each feature to have unit standard deviation.
    val scaledData = scalerModel.transform(dataFrame)
    println(scaledData.head(3).mkString("Array(", ", ", ")"))

    /**
     * [0,[1.0,2.0,4.0],[0.3333333333333333,0.6666666666666666,1.0]
     * [1,[4.0,5.0,6.0],[1.3333333333333333,1.6666666666666665,2.0]
     * [2,[7.0,8.0,9.0],[2.333333333333333,2.6666666666666665,3.0]
     *
     */
    //scaledData.show()
  }

  /**
   * RobustScaler：用来转换行向量数据集， 移除中位数，
   *
   * RobustScaler transforms a dataset of Vector rows, removing the median and
   * scaling the data according to a specific quantile range (by default the IQR: Interquartile Range, quantile range between the 1st quartile and the 3rd quartile).
   * Its behavior is quite similar to StandardScaler,
   * however the median and the quantile range are used instead of mean and standard deviation,
   * which make it robust to outliers.
   * 该输入参数如下：
   * lower: 默认值为：0.25. 计算分位数范围的下界（Lower quantile ），所有特征共享
   * upper：默认值为：0.75。 计算分位数范围的上界（Upper quantile ），所有特征共享
   * withScaling: 默认为True. 将数据scale到【lower，upper】内
   * withCentering: 默认为False. Centers the data with median before scaling. It will build a dense output, so take care when applying to sparse input.
   * RobustScaler 是一个Estimator，拟合一组数据集并产生一个RobustScalerModel;
   * this amounts to computing quantile statistics.
   * The model can then transform a Vector column in a dataset to have unit quantile range and/or zero median features.
   *
   * 注意：
   * Note that if the quantile range of a feature is zero, it will return default 0.0 value in the Vector for that feature.
   *
   * Examples
   *
   * The following example demonstrates how to load a dataset in libsvm format and then normalize each feature to have unit quantile range.
   *
   * @param spark
   */
  def robustScalerDemo(spark: SparkSession): Unit = {
    val dataFrame = spark.read.format("libsvm").load("learn-spark/data/mllib/sample_libsvm_data.txt")

    val scaler = new RobustScaler()
      .setInputCol("features")
      .setOutputCol("scaledFeatures")
      .setWithScaling(true)
      .setWithCentering(false)
      .setLower(0.25)
      .setUpper(0.75)

    // Compute summary statistics by fitting the RobustScaler.
    val scalerModel = scaler.fit(dataFrame)

    // Transform each feature to have unit quantile range.
    val scaledData = scalerModel.transform(dataFrame)
    scaledData.show()
  }

  /**
   * MinMaxScaler：
   * @param spark
   */
  def minMaxScalerDemo(spark: SparkSession): Unit = {

  }

  /**
   * MaxAbsScaler
   * @param spark
   */
  def mxAbsScalerDemo(spark: SparkSession): Unit = {

  }

  /**
   * Bucketizer：
   * @param spark
   */
  def bucketizerDemo(spark: SparkSession): Unit = {

  }

  /**
   * ElementwiseProduct：
   * @param spark
   */
  def elementwiseProductDemo(spark: SparkSession): Unit = {

  }

  /**
   * SQLTransformer：
   * @param spark
   */
  def sqlTransformerProductDemo(spark: SparkSession): Unit = {

  }

  /**
   * VectorAssembler：
   * @param spark
   */
  def vectorAssemblerProductDemo(spark: SparkSession): Unit = {

  }

  /**
   * VectorSizeHint：
   * @param spark
   */
  def vectorSizeHintProductDemo(spark: SparkSession): Unit = {

  }

  /**
   * Quantile： 分位数/分位点：是指将一个随机变量的概率分布范围分为几个等份的数值点，常用的有中位数（即二分位数）、四分位数、百分位数等。
   * Discretize： 离散化；使离散
   * QuantileDiscretizer：
   * @param spark
   */
  def quantileDiscretizerDemo(spark: SparkSession): Unit = {
    val data = Array((0, 18.0), (1, 19.0), (2, 8.0), (3, 5.0), (4, 2.2))
    val df = spark.createDataFrame(data).toDF("id", "hour")

    val discretizer = new QuantileDiscretizer()
      .setInputCol("hour")
      .setOutputCol("result")
      .setNumBuckets(3)

    val result = discretizer.fit(df).transform(df)
    result.show(false)
  }

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("CorrelationExample")
      .master("local[*]")
      .getOrCreate()

    //interactionDemo(spark)
    // normalizerDemo(spark)
    //standardScalerDemo(spark)
    quantileDiscretizerDemo(spark)
    //imputerDemo(spark)

    spark.stop()

  }

  /**
   * Imputer：填补确实数据， 默认按照已经存在的数据的平均值 或者 中位数 （median）填充
   *
   * Imputer 是一个estimator，用来完善数据集中缺失的数据，使用缺失值所在的行或者列的平均值或者中位数
   * Imputer产生的列必须是数值
   * 目前Imputer不支持分类特征，包含特征的列可能产生不正确的数据
   * Imputer可以impute用户自定义的数据，通过设置 .setMissingValue(custom_value)
   *  例如： .setMissingValue(0)将会补充数据0
   *  注：所有的null值都被会任务是缺失值，都需要补充数据
   * @param spark
   */
  def imputerDemo(spark: SparkSession): Unit = {
    val df = spark.createDataFrame(Seq(
      (1.0, Double.NaN),
      (2.0, Double.NaN),
      (Double.NaN, 3.0),
      (4.0, 4.0),
      (5.0, 5.0)
    )).toDF("a", "b")

    val imputer = new Imputer()
      .setInputCols(Array("a", "b"))
      .setOutputCols(Array("out_a", "out_b"))

    val model = imputer.fit(df)
    model.transform(df).show()

    /**
     * +---+---+-----+-----+
     * |  a|  b|out_a|out_b|
     * +---+---+-----+-----+
     * |1.0|NaN|  1.0|  4.0|
     * |2.0|NaN|  2.0|  4.0|
     * |NaN|3.0|  3.0|  3.0|
     * |4.0|4.0|  4.0|  4.0|
     * |5.0|5.0|  5.0|  5.0|
     * +---+---+-----+-----+
     */
  }



}
