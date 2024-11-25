package org.example.jucdemo2.volatiled;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class Volatile03Test {

    static boolean flag = true;

    /**
     * public volatile static boolean flag = true;
     *
     * 1. 当成员变量不加volatile时，程序不会停止
     * 2. 当成员变量加volatile时，程序会停止
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (flag) {
                //System.out.println("flag： " + (i++));
            }
            System.out.println("a end");
        },"a").start();

        TimeUnit.SECONDS.sleep(1);
        flag = false;
        System.out.println("Person flag is " + flag);
    }
}






















