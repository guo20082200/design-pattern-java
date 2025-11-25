package com.zishi.spark.ml.correlation

import org.apache.spark.sql.SparkSession

object DataSourcesDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("CorrelationExample")
      .master("local[*]")
      .getOrCreate()

    //imageDataSourceDemo01(spark)
    libsvmDemo01(spark)


    spark.stop()
  }


  /**
   * ImageDataSource：底层通过Java的ImageIO完成了图片加载的动作
   * 加载之后的schema包含一下内容：
   *
   * origin: StringType 图片的地址
   * height: IntegerType 图片的高度
   * width: IntegerType 图片的宽度
   * nChannels: IntegerType 图片的通道数
   * mode: IntegerType (OpenCV-compatible type)  图片的mode
   * data: 图片的二进制数据
   *
   * @param spark
   */
  def imageDataSourceDemo01(spark: SparkSession) : Unit = {
    val df = spark.read.format("image").option("dropInvalid", true).load("learn-spark/data/mllib/images/origin/kittens")

    /**
     * StructType(StructField(image,StructType(StructField(origin,StringType,true),StructField(height,IntegerType,true),StructField(width,IntegerType,true),StructField(nChannels,IntegerType,true),StructField(mode,IntegerType,true),StructField(data,BinaryType,true)),true))
     */
    println(df.schema)


    println(df.head) //[[file:///D:/my-learn/design-pattern-java/learn-spark/data/mllib/images/origin/kittens/54893.jpg,311,300,3,16,[B@4c579b5b]]

    df.select("image.origin", "image.width", "image.height", "image.nChannels", "image.mode").show(truncate=false)
  }


  /**
   * LIBSVM 数据源： sample_libsvm_data.txt 这种格式的
   *
   * label: DoubleType (represents the instance label)
   * features: VectorUDT (represents the feature vector)
   *
   * @param spark
   */
  def libsvmDemo01(spark: SparkSession) : Unit = {
    val df = spark.read.format("libsvm").option("numFeatures", "780").load("learn-spark/data/mllib/sample_libsvm_data.txt")

    /**
     * StructType(StructField(label,DoubleType,true),StructField(features,org.apache.spark.ml.linalg.VectorUDT@3bfc3ba7,true))
     */
    println(df.schema)

    df.show(10)

    /**
     * +-----+--------------------+
     * |label|            features|
     * +-----+--------------------+
     * |  0.0|(780,[127,128,129...|
     * |  1.0|(780,[158,159,160...|
     * |  1.0|(780,[124,125,126...|
     * |  1.0|(780,[152,153,154...|
     * |  1.0|(780,[151,152,153...|
     * |  0.0|(780,[129,130,131...|
     * |  1.0|(780,[158,159,160...|
     * |  1.0|(780,[99,100,101,...|
     * |  0.0|(780,[154,155,156...|
     * |  0.0|(780,[127,128,129...|
     * +-----+--------------------+
     */
    print(df.head)
  }
}
