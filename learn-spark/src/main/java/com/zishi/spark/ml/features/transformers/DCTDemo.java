package com.zishi.spark.ml.features.transformers;

/**
 * Java program to perform discrete cosine transform
 */
public class DCTDemo {


    public static int n = 4, m = 3;
    public static double pi = 3.142857;

    // Function to find discrete cosine transform and print it
    public static void dctTransform(double[][] matrix) {
        int i, j, k, l;

        // dct will store the discrete cosine transform
        double[][] dct = new double[m][n];

        double ci, cj, dct1, sum;

        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
                // ci and cj depends on frequency as well as
                // number of row and columns of specified matrix
                if (i == 0)
                    ci = 1 / Math.sqrt(m);
                else
                    ci = Math.sqrt(2) / Math.sqrt(m);

                if (j == 0)
                    cj = 1 / Math.sqrt(n);
                else
                    cj = Math.sqrt(2) / Math.sqrt(n);

                // sum will temporarily store the sum of
                // cosine signals
                sum = 0;
                for (k = 0; k < m; k++) {
                    for (l = 0; l < n; l++) {
                        dct1 = matrix[k][l] * Math.cos((2 * k + 1) * i * pi / (2 * m)) * Math.cos((2 * l + 1) * j * pi / (2 * n));
                        sum = sum + dct1;
                    }
                }
                dct[i][j] = ci * cj * sum;
            }
        }

        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
                System.out.printf("%f\t", dct[i][j]);
            }
            System.out.println();
        }

        /**
         * 2.309401	6.660587	3.459358	0.991531
         * -2.122609	-7.398432	-6.361443	-3.061238
         * 2.863396	0.586830	11.041690	-2.907468
         */
    }

    /**
     * Vectors.dense(0.0, 1.0, -2.0, 3.0),
     *       Vectors.dense(-1.0, 2.0, 4.0, -7.0),
     *       Vectors.dense(14.0, -2.0, -5.0, 1.0))
     * @param args
     */
    public static void main(String[] args) {
        double[][] matrix = {{0.0, 1.0, -2.0, 3.0},
                {-1.0, 2.0, 4.0, -7.0},
                {14.0, -2.0, -5.0, 1.0}};
        dctTransform(matrix);
    }
}

