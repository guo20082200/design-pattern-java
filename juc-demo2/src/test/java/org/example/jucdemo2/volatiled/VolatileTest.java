package org.example.jucdemo2.volatiled;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class VolatileTest {

    static boolean flag = true;

    @Test
    void test2() throws InterruptedException {
        new Thread(() -> {
            while (flag) {

            }
            System.out.println("adddd end");
        }, "xxx").start();

        TimeUnit.SECONDS.sleep(3);
        flag = false;
        System.out.println("flag is " + flag);
    }

    /**
     * public volatile static boolean flag = true;
     *
     * 1. 当成员变量不加volatile时，程序不会停止
     * 2. 当成员变量加volatile时，程序会停止
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        /*new Thread(() -> {
            while (flag) {

            }
            System.out.println("adddd end");
        }, "xxx").start();

        TimeUnit.SECONDS.sleep(3);
        flag = false;
        System.out.println("flag is " + flag);*/

        new Thread(() -> {
            int i = 0;
            while (Person.flag) {
                //System.out.println("person flag： " + (i++));
            }
            System.out.println("a end");
        },"a").start();

        TimeUnit.SECONDS.sleep(3);
        Person.flag = false;
        System.out.println("Person flag is " + Person.flag);
    }

    /**
     * 当前执行结果和main方法不一致，
     * 怀疑是 @Test 注解导致的
     * @throws InterruptedException
     */
    @Test
    void test() throws InterruptedException {

        new Thread(() -> {
            int i = 0;
            while (Person.flag) {
                //System.out.println("person flag： " + (i++));
            }
            System.out.println("a end");
        },"a").start();

        TimeUnit.SECONDS.sleep(3);
        Person.flag = false;


       /* new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Person.flag = false;
            System.out.println("ddddddddddddddd");
        },"b").start();*/

    }
}






















