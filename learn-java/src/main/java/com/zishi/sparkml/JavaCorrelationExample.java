package com.zishi.sparkml;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.ml.stat.Correlation;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.Arrays;
import java.util.List;


/**
 * An example for computing correlation matrix.
 * Run with
 * <pre>
 * bin/run-example ml.JavaCorrelationExample
 * </pre>
 */
public class JavaCorrelationExample {

    public static void main(String[] args) {
        // 创建SparkConf对象
        SparkConf conf = new SparkConf()
                .setAppName("KMeansExample")
                .setMaster("local[*]");

        // 创建SparkSession对象
        SparkSession spark = SparkSession.builder()
                .config(conf)
                .getOrCreate();

        // $example on$
        List<Row> data = Arrays.asList(
                RowFactory.create(Vectors.sparse(4, new int[]{0, 3}, new double[]{1.0, -2.0})),
                RowFactory.create(Vectors.dense(4.0, 5.0, 0.0, 3.0)),
                RowFactory.create(Vectors.dense(6.0, 7.0, 0.0, 8.0)),
                RowFactory.create(Vectors.sparse(4, new int[]{0, 3}, new double[]{9.0, 1.0}))
        );

        StructType schema = new StructType(new StructField[]{
                new StructField("features", new VectorUDT(), false, Metadata.empty()),
        });

        Dataset<Row> df = spark.createDataFrame(data, schema);
        Row r1 = Correlation.corr(df, "features").head();
        System.out.println("Pearson correlation matrix:\n" + r1.get(0).toString());

        Row r2 = Correlation.corr(df, "features", "spearman").head();
        System.out.println("Spearman correlation matrix:\n" + r2.get(0).toString());
        // $example off$

        spark.stop();
    }
}
