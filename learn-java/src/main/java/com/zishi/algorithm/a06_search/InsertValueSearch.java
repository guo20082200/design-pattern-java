package com.zishi.algorithm.a06_search;

public class InsertValueSearch {

    public static int insertValueSearch(int arr[], int value, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) * (value - arr[low]) / (arr[high] - arr[low]);//插值查找中间索引公式
            if (arr[mid] == value) {//若中间位置值等于我们所需要查找值，即返回中间值
                return mid;
            }
            if (arr[mid] < value) {//若中间位置值小于我们所需查找值，则向右递归且将下界值变为mid+1
                return insertValueSearch(arr, value, mid + 1, high);
            }
            if (arr[mid] > value) {//若中间位置值大于我们所需查找值，则向左递归将上界值变为mid-1
                return insertValueSearch(arr, value, low, mid - 1);
            }
        }
        return -1;
    }
}
