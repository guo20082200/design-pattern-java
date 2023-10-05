package com.zishi.algorithm.a05_sort;

/**
 * 基本介绍
 * 选择式排序也属于内部排序法，是从欲排序的数据中，按指定的规则选出某一元素，再依规定交换位置后达到排序的目的
 * <p>
 * 基本思想
 * 选择排序（select sorting）也是一种简单的排序方法。它的基本思想是：
 * 第一次从 arr[0]~arr[n-1]中选取最小值， 与 arr[0]交换，
 * 第二次从 arr[1]~arr[n-1]中选取最小值，与 arr[1]交换，
 * 第三次从 arr[2]~arr[n-1]中选取最小值，与 arr[2] 交换，…，
 * 第 i 次从 arr[i-1]~arr[n-1]中选取最小值，与 arr[i-1]交换，…,
 * 第 n-1 次从 arr[n-2]~arr[n-1]中选取最小值， 与 arr[n-2]交换，
 * 总共通过 n-1 次，得到一个按排序码从小到大排列的有序序列。
 */
public class SelectSorting {

    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (arr.length * 100));
        }
        long start = System.currentTimeMillis();
        selectSort(arr);
        long end = System.currentTimeMillis();
        System.out.println("排序后的时间：" + (end - start));
    }

    //选择排序
    public static void selectSort(int[] arr) {
        //使用逐步推导的方式来，讲解选择排序
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;//假定最小数的索引
            int min = arr[minIndex];//假定最小数的值
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {//说明这个最小值并不是最小
                    min = arr[j];
                    minIndex = j;
                }
            }
            //交换
            //多一个判断条件
            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
            //System.out.println(Arrays.toString(arr));
        }
    }
}
