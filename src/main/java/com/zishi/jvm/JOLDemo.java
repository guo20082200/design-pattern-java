package com.zishi.jvm;

import org.openjdk.jol.info.ClassLayout;

public class JOLDemo {
    public static void main(String[] args) {
        Car car = Car.builder()
                .id(1)
                .type("SUV")
                .level('A')
                .price(22.22)
                .build();


        System.out.println( ClassLayout.parseInstance(car).toPrintable() );
        
        int[] array = new int[3];
        array[0] = 11;
        array[1] = 22;
        array[2] = 33;
        System.out.println( ClassLayout.parseInstance(array).toPrintable() );

    }
}