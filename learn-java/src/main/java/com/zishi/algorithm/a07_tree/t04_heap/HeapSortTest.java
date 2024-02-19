package com.zishi.algorithm.a07_tree.t04_heap;

public class HeapSortTest {

    public static void main(String[] args) {

        int[] arr = new int[800000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (arr.length * 100));
        }
        long start = System.currentTimeMillis();
        // 80000 -> 11ms
        // 800000 -> 100ms
        // 8000000 -> 1910ms
        HeapSort.heapSort(arr);
        long end = System.currentTimeMillis();
        System.out.println("排序后的时间：" + (end - start));
    }
}
