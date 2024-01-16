package com.zishi.algorithm.a05_sort;

/**
 * 插入排序的介绍
 * 插入式排序属于内部排序法，是对于欲排序的元素以插入的方式找寻该元素的适当位置，以达到排序的目的。
 * <p>
 * 插入排序的思想
 * 插入排序（Insertion Sorting）的基本思想是：
 * 把 n 个待排序的元素看成为一个有序表和一个无序表，
 * 开始时有序表中只包含一个元素，
 * 无序表中包含有 n-1 个元素，
 * 排序过程中每次从无序表中取出第一个元素，
 * 把它的排序码依次与有序表元素的排序码进行比较，
 * 将它插入到有序表中的适当位置，使之成为新的有序表。
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (arr.length * 100));
        }
        long start = System.currentTimeMillis();
        insertSort(arr); // 436ms
        long end = System.currentTimeMillis();
        System.out.println("排序后的时间：" + (end - start));
    }

    public static void insertSort(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            int insertVal = arr[i];
            int insertIndex = i - 1;
            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }
            //判断是否需要赋值
            arr[insertIndex + 1] = insertVal;
        }
    }
}
