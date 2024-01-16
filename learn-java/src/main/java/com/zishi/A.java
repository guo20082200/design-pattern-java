package com.zishi;


// import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

public class A {

    public static void main(String[] args) {

        String s = "  erfaer  ";
        boolean blank = s.isBlank();
        boolean empty = s.isEmpty();

        // String.strip() ， String.stripLeading()和String.stripTrailing()方法修剪目标String的正面，背面或正面和背面的空白

        System.out.println(s.strip());
        System.out.println(s.stripLeading());
        System.out.println(s.stripTrailing());

        //
        String repeat = s.repeat(3);
        System.out.println(repeat);

        // String.lines()方法拆分由其行终止符在其上调用的String ，并返回由这些行终止符划分的Strings Stream
    }
}
