package com.zishi.algorithm.a06_search;

import java.util.ArrayList;
import java.util.List;

public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1,8,10,98,1000,1000,1234};
        List<Integer> integers = binarySearch(arr, 0, arr.length - 1, 1000);
        for (Integer integer : integers) {
            System.out.print(integer + " ");
        }


    }

    /**
     * 非递归查找
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchIterative(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2; // 防止溢出，使用这种方法计算中间位置

            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        // 如果未找到目标值，返回-1表示未找到
        return -1;
    }


    /**
     * 二分查找递归算法
     * @param array
     * @param target
     * @param left
     * @param right
     * @return
     */
    public static int binarySearchRecursive(int[] array, int target, int left, int right) {
        if (left > right) {
            return -1; // 表示未找到目标值
        }

        int mid = left + (right - left) / 2;

        if (array[mid] == target) {
            return mid;
        } else if (array[mid] < target) {
            return binarySearchRecursive(array, target, mid + 1, right);
        } else {
            return binarySearchRecursive(array, target, left, mid - 1);
        }
    }

    /**
     * 递归查找
     * @param arr
     * @param left
     * @param right
     * @param findVal
     * @return
     */
    public static List<Integer> binarySearch(int[] arr,int left,int right,int findVal){
        if (left > right) return new ArrayList<Integer>();
        int mid = (left + right)/2;
        int midVal = arr[mid];

        if (findVal > midVal){
            return binarySearch(arr,mid+1,right,findVal);
        }else if(findVal < midVal){
            return binarySearch(arr,left,mid-1,findVal);
        }else {
            List<Integer> resIndexlist = new ArrayList<>();
            int temp = mid -1;
            //向mid左边开始继续搜索
            while(true){
                if (temp < 0|| arr[temp] != findVal){//退出
                    break;
                }
                resIndexlist.add(temp);
                temp -= 1;
            }
            resIndexlist.add(mid);
            //向mid右边继续搜索
            temp = mid +1;
            while (true){
                if(temp > (arr.length-1) || arr[temp]!= findVal){
                    break;
                }
                resIndexlist.add(temp);
                temp+=1;
            }
           return resIndexlist;
        }

    }
}
