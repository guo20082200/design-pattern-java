package com.zishi.algorithm.a06_search;

import java.util.Arrays;

public class FibSearch {
    public static int maxSize = 20; //假定斐波那契数列大小为20

    public static void main(String[] args) {
        int[] arr = {1, 8, 48, 88, 89, 899, 1024};
        System.out.println("index=" + fibSearch(arr, 1024));
    }

    //首先得到一个斐波那契数列
    public static int[] fib() {
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    /**
     * @param arr 数组
     * @param key key我们需要查找的值
     * @return 返回对应下标，未找到返回-1
     */
    public static int fibSearch(int[] arr, int key) {
        int low = 0;
        int high = arr.length - 1;
        int k = 0;  //表示斐波那契分割数值的下标
        int mid = 0;    //存放mid值

        //获取斐波那契数列
        int f[] = fib();

        //获取斐波那契分割数值的下标
        while (high > f[k] - 1) {
            k++;
        }

        //因为f【k】，可能大于数组长度，因此需要不齐
        //不足部分使用0填充
        int[] temp = Arrays.copyOf(arr, f[k]);
        //用数组最后的数 填充temp
        //举例：
        //temp={1, 8, 48, 88, 89, 899, 1024，0，0} => {1, 8, 48, 88, 89, 899, 1024，1024，1024}
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }

        //使用while循环找到key
        while (low <= high) {
            //System.out.println("..............");
            mid = low + f[k - 1] - 1;//得到mid的下标

            if (key < temp[mid]) {//继续向数组前部分查找（左边）
                high = mid - 1;
                //为什么是k--;
                //说明：
                //1.全部元素=前边元素+后边元素
                //2.f[k] = f[k-1] + f[k-2]
                //因为前面的 f[k-1] 个元素，所以我们可以继续拆分，f[k-1] = f[k-2] + f[k-3]
                //即再f[k-1]的前面继续查找，k--
                k--;
            } else if (key > temp[mid]) {//继续向数组后部分查找（右边）
                low = mid + 1;
                //为什么是k-2；
                //说明：
                //1.全部元素=前边元素+后边元素
                //2.f[k] = f[k-1] + f[k-2]
                //3.后面右 f[k-2] 个元素，继续拆分
                //即下次循环 mid = f[k-1-2] - 1。
                k -= 2;
            } else {//找到
                //需要确定，返回哪个值
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }
        return -1;
    }
}
