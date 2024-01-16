package com.zishi.algorithm.a05_sort;

import java.util.Arrays;

/**
 * 插入排序的缺点： 当需要插入的数是较小的数时，后移的次数明显增多，对效率有影响.
 * 介绍
 * 希尔排序是希尔（Donald Shell）于1959年提出的一种排序算法。希尔排序也是一种插入排序，它是简单插入排序经过改进之后的一个更高效的版本，也称为缩小增量排序。
 * <p>
 * 基本思想
 * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = new int[8000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (arr.length * 100));
        }
        long start = System.currentTimeMillis();
        // shellSort(arr); // 5313ms
        shellSort2(arr); // 1726ms
        long end = System.currentTimeMillis();
        System.out.println("排序后的时间：" + (end - start));
    }

    /**
     * shell排序交换法
     *
     * @param arr
     */
    public static void shellSort(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {//确定每一次的步长
            for (int i = gap; i < arr.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        int temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
            // System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * shell排序移位法，局部的插入排序
     *
     * @param arr
     */
    public static void shellSort2(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {//确定每一次的步长
            //从第gap个元素，逐个对其所在的组进行直接插入排序
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j - gap]) {
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        //移动
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    //退出循环，找到位置
                    arr[j] = temp;
                }
            }
            // System.out.println(Arrays.toString(arr));
        }
    }
}
