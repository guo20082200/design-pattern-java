package com.zishi.algorithm.a05_sort;

public class RadixSort {

    public static void main(String[] args) {

        int[] arr = new int[8000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (arr.length * 100));
        }
        long start = System.currentTimeMillis();
        radixSort(arr); // 740ms
        long end = System.currentTimeMillis();
        System.out.println("排序后的时间：" + (end - start));
    }


    public static void radixSort(int[] arr) {
        //第一轮排序（针对个位进行处理）
        //定义一个二维数组代表10个桶，每一个桶就是一个一维数组
        int[][] bucket = new int[10][arr.length];
        //为了记录每一个桶中实际存放了多少个数据，我们定义一个一维数组来记录各个桶每次放入的数据个数
        int[] bucketElementCounts = new int[10];//记录每个桶中数据的数量
        int max = arr[0]; // 找到数组中的最大元素
        for (int j : arr) {
            if (j > max) {
                max = j;
            }
        }

        int maxLength = (String.valueOf(max)).length();//数组中的最大元素是几位数
        int n = 1;
        for (int i = 0; i < maxLength; i++) {
            for (int value : arr) {
                //取出每个元素的个/十/百位
                int digitOfElement = value / n % 10;
                //放入到对应的桶中
                //数组中的第一个数代表这0.1.2...
                //数组中第二个数代表每一个桶中有多少个数
                //bucketElementCounts[digitOfElement] 代表着每一个桶中，元素的索引
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = value;
                bucketElementCounts[digitOfElement]++;
            }
            //放入原数组
            int index = 0;
            //遍历每一个桶，并将桶中的数据放入到原数组
            for (int k = 0; k < bucket.length; k++) {
                //如果桶中有数据，我们才放入到原数组
                if (bucketElementCounts[k] != 0) {
                    //说明桶中有数据
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        //取出元素放入到arr
                        arr[index] = bucket[k][l];
                        index++;
                    }
                }
                bucketElementCounts[k] = 0;//处理完一个桶之后需要将桶置为0
            }
            n *= 10;
        }

    }
}
