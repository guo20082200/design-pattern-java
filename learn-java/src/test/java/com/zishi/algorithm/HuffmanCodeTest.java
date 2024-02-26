package com.zishi.algorithm;

import com.zishi.algorithm.a07_tree.t05_huffman.HuffmanCode;
import org.junit.jupiter.api.Test;

class HuffmanCodeTest {

   private static final String content = "abbcccdddd";

    @Test
    void compress() {
        byte[] bytes = content.getBytes();
        //System.out.println(bytes.length);
        byte[] compress = HuffmanCode.compress(bytes);
        //System.out.println(compress.length);

//        System.out.println(Integer.toBinaryString(1));
//        byte b = Byte.parseByte("0000000100000001");
//        System.out.println(b);

        byte[] uncompress = HuffmanCode.uncompress(compress);
        System.out.println(new String(uncompress));
    }

    @Test
    void uncompress() {

        String s = Integer.toBinaryString((byte) 3);
        System.out.println(s);

        String s2 = Byte.toString((byte)-3);
        System.out.println(s2);
    }
}