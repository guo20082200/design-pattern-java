package com.zishi.jvm.jol.ab;

import org.openjdk.jol.info.ClassLayout;

public class JOLSample_01_Basic {

    public static void main(String[] args) {

        System.out.println(ClassLayout.parseClass(GrandFather.class).toPrintable());

        System.out.println("-----------------------------------------------------------------");


        System.out.println(ClassLayout.parseClass(Father.class).toPrintable());
        System.out.println("-----------------------------------------------------------------");
        System.out.println(ClassLayout.parseClass(Son.class).toPrintable());
    }

}