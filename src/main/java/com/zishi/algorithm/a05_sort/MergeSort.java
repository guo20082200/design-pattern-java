package com.zishi.algorithm.a05_sort;

public class MergeSort {

    /**
     * @param arr   原始数组
     * @param left  左边序列的初始索引
     * @param mid   中间索引
     * @param right 右边索引
     * @param temp  做中转的数组
     */
    public static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;//初始化i，左边有序序列的初始索引
        int j = mid + 1;//初始化j，右边有序序列的初始索引
        int index = 0; //中间数组的索引
        //count++;

        //先把左右两边的数据，按规则拷贝到temp中
        //直到左右两边有一侧处理完毕为止
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[index] = arr[i];
                index++;
                i++;
            } else {
                temp[index] = arr[j];
                index++;
                j++;
            }
        }
        //把有剩余数据的一边，剩余的数据一次放入temp中
        while (i <= mid) {
            temp[index] = arr[i];
            index++;
            i++;
        }
        while (j <= right) {
            temp[index] = arr[j];
            index++;
            j++;
        }

        //将temp数组中的原数放入arr中
        index = 0; //将索引置0
        int tempLeft = left;
        while (tempLeft <= right) {
            arr[tempLeft] = temp[index];
            index++;
            tempLeft++;
        }
    }
}
