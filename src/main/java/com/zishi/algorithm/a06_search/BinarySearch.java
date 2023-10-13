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
