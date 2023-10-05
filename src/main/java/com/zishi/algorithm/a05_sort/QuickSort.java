package com.zishi.algorithm.a05_sort;

public class QuickSort {

    public static void main(String[] args) {

        /*int[] arr = {30, 24, 5, 58, 18, 36, 12, 42, 39};
        quickSort(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));*/ // [5, 12, 18, 24, 30, 36, 39, 42, 58]

        int[] arr = new int[8000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (arr.length * 100));
        }
        long start = System.currentTimeMillis();
        // shellSort(arr); // 5313ms
        quickSort(arr); // 757ms
        long end = System.currentTimeMillis();
        System.out.println("排序后的时间：" + (end - start));
    }

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int low, int height) {
        int mid;
        if (low < height) {
            mid = part(arr, low, height);  // 返回基准元素位置
            quickSort(arr, low, mid - 1); // 左区间递归快速排序
            quickSort(arr, mid + 1, height); // 右区间递归快速排序
        }
    }

    public static int part(int[] arr, int low, int height) {  //划分函数
        int i = low, j = height, pivot = arr[low]; //基准元素
        while (i < j) {
            while (i < j && arr[j] > pivot) { //从右向左开始找一个 小于等于 pivot的数值
                j--;
            }
            if (i < j) {
                int temp;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                //swap(arr[i++], arr[j]);  //r[i]和r[j]交换后 i 向右移动一位
                i++;
            }
            while (i < j && arr[i] <= pivot) { //从左向右开始找一个 大于 pivot的数值
                i++;
            }
            if (i < j) {
                int temp;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                j--;
                //swap(arr[i], arr[j--]);  //r[i]和r[j]交换后 i 向左移动一位
            }
        }
        return i;  //返回最终划分完成后基准元素所在的位置
    }
}
