package com.zishi.algorithm.a09_algorithom;

import java.util.Arrays;

/**
 * 解决01背包问题
 */
public class DynamicProgramming {

    private static int[] weight = {0, 2, 3, 4, 5};            //商品的体积2、3、4、5
    private static int[] value = {0, 3, 4, 5, 6};
    private static final int BAG_CAPACITY = 8;

    private static int[][] dp = new int[weight.length][BAG_CAPACITY + 1];

    public static void main(String[] args) {
        print();
        findMax();
        System.out.println("...........");
        print();

        findWhat(4, 8);
        System.out.println("...........");
        System.out.println(Arrays.toString(item));
    }

    public static void print() {
        for (int[] ele : dp) {
            System.out.println(Arrays.toString(ele));
        }
    }


    /**
     * 动态规划
     */
    public static void findMax() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= BAG_CAPACITY; j++) {
                if (j < weight[i])
                    dp[i][j] = dp[i - 1][j];
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
            }
        }
    }

    private static final int[] item = new int[5];

    public static void findWhat(int i, int j) {                //最优解情况
        if (i >= 1) {
            if (dp[i][j] == dp[i - 1][j]) {
                item[i] = 0;
                findWhat(i - 1, j);
            } else if (j - weight[i] >= 0 && dp[i][j] == dp[i - 1][j - weight[i]] + value[i]) {
                item[i] = 1;
                findWhat(i - 1, j - weight[i]);
            }
        }
    }

}
