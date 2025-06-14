package com.zishi.spark.ml.features;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class DCTImageProcessor {
    // 量化表（JPEG亮度标准量化表）
    private static final int[][] QUANTIZATION_MATRIX = {
            {16, 11, 10, 16, 24, 40, 51, 61},
            {12, 12, 14, 19, 26, 58, 60, 55},
            {14, 13, 16, 24, 40, 57, 69, 56},
            {14, 17, 22, 29, 51, 87, 80, 62},
            {18, 22, 37, 56, 68, 109, 103, 77},
            {24, 35, 55, 64, 81, 104, 113, 92},
            {49, 64, 78, 87, 103, 121, 120, 101},
            {72, 92, 95, 98, 112, 100, 103, 99}
    };

    // 块大小（8x8是JPEG标准）
    private static final int BLOCK_SIZE = 8;

    /*public static void main(String[] args) {
        try {
            // 读取原始图像
            BufferedImage originalImage = ImageIO.read(new File("input.jpg"));

            // 应用DCT处理
            BufferedImage processedImage = processImage(originalImage);

            // 保存处理后的图像
            File output = new File("output.jpg");
            ImageIO.write(processedImage, "jpg", output);

            System.out.println("图像处理完成并保存到: " + output.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    /**
     * 处理整幅图像
     */
    public static BufferedImage processImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 图像分块处理
        for (int y = 0; y < height; y += BLOCK_SIZE) {
            for (int x = 0; x < width; x += BLOCK_SIZE) {
                // 确保块不超出图像边界
                if (x + BLOCK_SIZE > width || y + BLOCK_SIZE > height) {
                    continue;
                }

                // 提取8x8像素块并转换为YUV颜色空间
                double[][] yBlock = new double[BLOCK_SIZE][BLOCK_SIZE];

                for (int j = 0; j < BLOCK_SIZE; j++) {
                    for (int i = 0; i < BLOCK_SIZE; i++) {
                        Color color = new Color(image.getRGB(x + i, y + j));
                        // 只处理亮度通道(Y)进行简化
                        yBlock[i][j] = rgbToY(color.getRed(), color.getGreen(), color.getBlue()) - 128;
                    }
                }

                // 应用DCT变换
                double[][] dctBlock = applyDCT(yBlock);

                // 量化
                int[][] quantizedBlock = quantize(dctBlock);

                // 反量化
                double[][] dequantizedBlock = dequantize(quantizedBlock);

                // 应用IDCT变换
                double[][] idctBlock = applyIDCT(dequantizedBlock);

                // 将处理后的块放回图像
                for (int j = 0; j < BLOCK_SIZE; j++) {
                    for (int i = 0; i < BLOCK_SIZE; i++) {
                        // 限制像素值在0-255范围内
                        int y2 = (int) Math.round(idctBlock[i][j] + 128);
                        y2 = Math.max(0, Math.min(255, y));

                        // 简化处理，假设YUV的U和V分量不变
                        int u = 128;
                        int v = 128;

                        // 转回RGB
                        int rgb = yuvToRgb(y, u, v);
                        result.setRGB(x + i, y2 + j, rgb);
                    }
                }
            }
        }

        return result;
    }

    /**
     * 一维 DCT变换
     *
     * @param input 输入信息
     * @return
     */
    public static double[] applyDCT1D(double[] input) {
        int N = input.length;

        double alpha01 = 1 / Math.sqrt(N);
        double alpha02 = Math.sqrt(2.0 / N);
        double[] result = new double[N];


        for (int j = 0; j < result.length; j++) {
            double sum = 0.0;
            for (int i = 0; i < input.length; i++) {
                double d = input[i] * Math.cos(((2 * i + 1) * j * Math.PI) / (2 * N));
                sum += d;
            }
            double alpha = j == 0 ? alpha01 : alpha02;
            result[j] = alpha * sum;
        }

        return result;
    }

    public static void main(String[] args) {
        //double[] input = {1.0, 2.0, 3.0, 4.0};
        // [5.0, -2.230442497387663, -6.280369834735101E-16, -0.15851266778110815]
        // 直流分量F(0)最大，体现信号的均值；高频分量（如F(3)）数值较小，在压缩中可被近似或舍弃
        //

        double[][] input = {{1.0, 3.0}, {2.0, 4.0}};
        System.out.println(Arrays.toString(applyDCT1D(input[0]))); // [2.82842712474619, -1.414213562373095]
        System.out.println(Arrays.toString(applyDCT1D(input[1]))); // [4.242640687119285, -1.4142135623730947]
    }

    /**
     * 应用二维DCT变换
     */
    public static double[][] applyDCT(double[][] input) {
        int rows = input.length;
        int cols = input[0].length;

        double[][] result = new double[rows][cols];
        //todo: 步骤 1：计算行方向一维 DCT（逐行处理）

        //todo: 步骤 2：计算列方向一维 DCT（逐列处理中间矩阵）

        /*
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                double sum = 0.0;

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        double cosU = Math.cos(((2 * i + 1) * u * Math.PI) / (2 * N));
                        double cosV = Math.cos(((2 * j + 1) * v * Math.PI) / (2 * N));
                        sum += block[i][j] * cosU * cosV;
                    }
                }

                double alphaU = (u == 0) ? Math.sqrt(1.0 / N) : Math.sqrt(2.0 / N);
                double alphaV = (v == 0) ? Math.sqrt(1.0 / N) : Math.sqrt(2.0 / N);

                result[u][v] = alphaU * alphaV * sum;
            }
        }*/

        return result;
    }

    /**
     * 应用二维IDCT变换
     */
    public static double[][] applyIDCT(double[][] block) {
        double[][] result = new double[BLOCK_SIZE][BLOCK_SIZE];
        int N = BLOCK_SIZE;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                double sum = 0.0;

                for (int u = 0; u < N; u++) {
                    for (int v = 0; v < N; v++) {
                        double alphaU = (u == 0) ? Math.sqrt(1.0 / N) : Math.sqrt(2.0 / N);
                        double alphaV = (v == 0) ? Math.sqrt(1.0 / N) : Math.sqrt(2.0 / N);

                        double cosU = Math.cos(((2 * i + 1) * u * Math.PI) / (2 * N));
                        double cosV = Math.cos(((2 * j + 1) * v * Math.PI) / (2 * N));

                        sum += alphaU * alphaV * block[u][v] * cosU * cosV;
                    }
                }

                result[i][j] = sum;
            }
        }

        return result;
    }

    /**
     * 量化处理
     */
    public static int[][] quantize(double[][] block) {
        int[][] result = new int[BLOCK_SIZE][BLOCK_SIZE];

        for (int i = 0; i < BLOCK_SIZE; i++) {
            for (int j = 0; j < BLOCK_SIZE; j++) {
                result[i][j] = (int) Math.round(block[i][j] / QUANTIZATION_MATRIX[i][j]);
            }
        }

        return result;
    }

    /**
     * 反量化处理
     */
    public static double[][] dequantize(int[][] block) {
        double[][] result = new double[BLOCK_SIZE][BLOCK_SIZE];

        for (int i = 0; i < BLOCK_SIZE; i++) {
            for (int j = 0; j < BLOCK_SIZE; j++) {
                result[i][j] = block[i][j] * QUANTIZATION_MATRIX[i][j];
            }
        }

        return result;
    }

    /**
     * RGB转YUV（亮度）
     */
    public static double rgbToY(int r, int g, int b) {
        return 0.299 * r + 0.587 * g + 0.114 * b;
    }

    /**
     * YUV转RGB
     */
    public static int yuvToRgb(int y, int u, int v) {
        int r = (int) (y + 1.402 * (v - 128));
        int g = (int) (y - 0.34414 * (u - 128) - 0.71414 * (v - 128));
        int b = (int) (y + 1.772 * (u - 128));

        // 限制在0-255范围内
        r = Math.max(0, Math.min(255, r));
        g = Math.max(0, Math.min(255, g));
        b = Math.max(0, Math.min(255, b));

        return new Color(r, g, b).getRGB();
    }
}    