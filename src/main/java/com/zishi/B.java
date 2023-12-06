package com.zishi;


// import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class B {

    public static void main(String[] args) {


        byte[] bys = new byte[1];
        bys[0] = -96;
        String s = new String(bys);
        System.out.print(s);
        //System.out.println(s.toCharArray());

        String binaryString = Integer.toBinaryString((byte)-96);
        System.out.println(binaryString);

        byte ivalue = -96;
        String binary = Integer.toBinaryString(ivalue);
        System.out.println("binaryString = " + binary);


        byte[] bytes = "�".getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length);
        System.out.println(Arrays.toString(bytes)); // [-17, -65, -67]

        System.out.println("A".getBytes().length);
        System.out.println("-3".getBytes().length);
        System.out.println("-96".getBytes().length);
        System.out.println(Arrays.toString("-".getBytes()));
        System.out.println(Arrays.toString("-96".getBytes())); // [45, 57, 54]

    }
}
