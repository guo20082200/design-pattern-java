package org.example.jucdemo2.volatiled;


public class Person {


    //public static boolean flag = true;
    public volatile static boolean flag = true;
    public volatile int number = 1;

    public void addOne() {
        number += 1;
    }
}