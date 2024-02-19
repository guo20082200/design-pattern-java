package com.zishi.algorithm.a07_tree.t04_heap;

import java.util.Arrays;

/**
 * 大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2]
 * 小顶堆：arr[i] <= arr[2i+1] && arr[i] <= arr[2i+2]
 */
public class HeapSort {

    // 建立大顶堆（仅适用于非叶子节点）
    public static void buildMaxHeap(int[] arr, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, n, i);
        }
    }

    /**
     * 调整堆，使其满足大顶堆性质
     * @param arr 数组
     * @param n 数组的长度
     * @param i 最大元素的索引
     */
    private static void adjustHeap(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            adjustHeap(arr, n, largest);
        }
    }


    // 交换数组中的两个元素
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    // 堆排序方法
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // 构建大顶堆
        buildMaxHeap(arr, n);

        // System.out.println("...............................");
        // 依次将堆顶元素与末尾元素交换，并重新调整堆
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            // 由于交换后堆的大小减一，因此不需要对最后一个元素再进行heapfy
            adjustHeap(arr, i, 0);
            //System.out.println(Arrays.toString(arr));
        }
    }

    /*public static void main(String[] args) {
        int[] arr = {6, 9, 1, 4, 5, 8, 7, 0, 2, 3};
        heapSort(arr);
    }*/
}
