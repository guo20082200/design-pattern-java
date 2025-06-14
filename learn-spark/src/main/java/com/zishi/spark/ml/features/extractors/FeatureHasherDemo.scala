package com.zishi.spark.ml.features.extractors

import org.apache.spark.ml.feature.FeatureHasher
import org.apache.spark.sql.SparkSession

/**
 * 特征哈希通过哈希技巧将一组类别型或数值型特征映射到一个指定维度（通常远小于原始特征空间维度）的特征向量中。
 * 特征哈希器（FeatureHasher）转换器可以同时处理多个列。每一列可能包含数值型或类别型特征。
 * 对列数据类型的处理方式如下：
 * 数值型列：对于数值型特征，使用列名的哈希值来映射特征值到特征向量的索引位置。默认情况下，数值型特征不会被当作类别型特征处理（即使它们是整数）。若要将它们作为类别型特征处理，需通过参数 categoricalCols 指定相关列。
 * 字符串列：对于类别型特征，使用字符串“列名=值”的哈希值来映射到向量索引，指示值为1.0。因此，类别型特征采用“独热编码”（类似于使用 OneHotEncoder 且 dropLast=false）。
 * 布尔列：布尔值的处理方式与字符串列相同。即布尔特征表示为“列名=true”或“列名=false”，指示值为1.0。缺失值（Null）会被忽略（在结果特征向量中隐式为零）。
 * 该方法使用的哈希函数是与 HashingTF 中相同的 MurmurHash 3。由于使用简单的哈希值模运算来确定向量索引，建议将 numFeatures 参数设置为2的幂次；否则，特征可能无法均匀地映射到向量索引上。
 */
object FeatureHasherDemo {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("CorrelationExample")
      .master("local[*]")
      .getOrCreate()


    demo01(spark)

    spark.stop()

  }

  def demo01(spark: SparkSession): Unit = {
    // $example on$
    val dataset = spark.createDataFrame(Seq(
      (2.2, true, "1", "foo"),
      (3.3, false, "2", "bar"),
      (4.4, false, "3", "baz"),
      (5.5, false, "4", "foo")
    )).toDF("real", "bool", "stringNum", "string")

    val hasher = new FeatureHasher()
      .setInputCols("real", "bool", "stringNum", "string")
      .setOutputCol("features")
      //.setNumFeatures(1024)

    val featurized = hasher.transform(dataset)
    featurized.show(false)

    /**
     * 输出结果如下：
     * +----+-----+---------+------+--------------------------------------------------------+
     * |real|bool |stringNum|string|features                                                |
     * +----+-----+---------+------+--------------------------------------------------------+
     * |2.2 |true |1        |foo   |(262144,[174475,247670,257907,262126],[2.2,1.0,1.0,1.0])|
     * |3.3 |false|2        |bar   |(262144,[70644,89673,173866,174475],[1.0,1.0,1.0,3.3])  |
     * |4.4 |false|3        |baz   |(262144,[22406,70644,174475,187923],[1.0,1.0,4.4,1.0])  |
     * |5.5 |false|4        |foo   |(262144,[70644,101499,174475,257907],[1.0,1.0,5.5,1.0]) |
     * +----+-----+---------+------+--------------------------------------------------------+
     */
  }


}
