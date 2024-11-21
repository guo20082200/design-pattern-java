package org.example.jucdemo2.volatiled;

import java.util.concurrent.TimeUnit;

/**
 * volatile 不具备原子性,
 */
public class VolatileTest02 {

    static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        Person person = new Person();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    person.addOne();
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(2);
        System.out.println(person.number);

    }

}






















