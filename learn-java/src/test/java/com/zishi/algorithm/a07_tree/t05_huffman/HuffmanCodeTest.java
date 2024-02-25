package com.zishi.algorithm.a07_tree.t05_huffman;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanCodeTest {

    //public static final String CONTENT = "Returns an array containing all of the elements in this list in proper sequence (from first to last element); the runtime type of the returned array is that of the specified array. If the list fits in the specified array, it is returned therein. Otherwise, a new array is allocated with the runtime type of the specified array and the size of this list.";


    public static final String CONTENT = "Returns an array containing";
    @Test
    void zip() {

        //1. 将字符串转为byte[]
        byte[] contentBytes = CONTENT.getBytes();
        System.out.println("contentBytes:" + contentBytes.length);
        //2. 压缩byte[]，输出byte[]
        byte[] zipBytes = HuffmanCode.zip(contentBytes);
        System.out.println("zipBytes:" + zipBytes.length);
        //3. 解压缩byte[]
        byte[] unzipBytes = HuffmanCode.unzip(zipBytes);
        //4. 将byte[]转换为字符串
        String newStr = new String(unzipBytes);
        System.out.println("unzipBytes:" + unzipBytes.length);
        System.out.println(CONTENT.equals(newStr));

    }

    @Test
    void unzip() {
    }
}