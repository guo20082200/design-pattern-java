package com.zishi.scala.okk.ml.linear;

/**
 * 线性回归，一元一次函数（二元线性回归） y = ax + b
 *
 * 多元线性回归：
 * y = a0 + a_1 * x_1 + a_2 * x_2 + .... + a_n * x_n
 */
public class LeastSquaresMethod {

    // 计算均值
    public static double mean(double[] array) {
        double sum = 0.0;
        for (double v : array) {
            sum += v;
        }
        return sum / array.length;
    }

    /**
     * 普通最小二乘法给出的判断标准是：残差平方和的值达到最小
     * 计算最小二乘法的斜率a和截距b
     * @param x: 输入x向量
     * @param y: 输入y向量
     * @return 斜率和截距
     */
    public static double[] linearLeastSquares(double[] x, double[] y) {
        int n = x.length;
        double sumX = 0.0;
        double sumY = 0.0;
        double sumXY = 0.0;
        double sumX2 = 0.0;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumX2 += x[i] * x[i];
        }

        double a = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double b = (sumY - a * sumX) / n;

        return new double[]{a, b}; // 返回斜率a和截距b
    }

    /**
     * 多元线性回归求解，通过矩阵的偏导数为0求解
     *
     * 系数矩阵= ([(inputX转置) * inputX]求逆) * inputX转置 *
     *
     * @param inputX：输入的x变量，二维矩阵
     * @param inputY：输入的y变量，一维向量
     */
    public static double[] multiLinearRegression(double[][] inputX, double[] inputY) {




        //return new double[]{a, b};
        return null;
    }

    public static void main(String[] args) {
        double[] x = {540, 360, 240, 480, 420};
        double[] y = {205, 325, 445, 505, 455};

        double[] result = linearLeastSquares(x, y);
        System.out.println("斜率a: " + result[0]);
        System.out.println("截距b: " + result[1]);
    }
}
