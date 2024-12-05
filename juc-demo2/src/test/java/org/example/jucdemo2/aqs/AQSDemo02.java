package org.example.jucdemo2.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo02 {
    private static int num;


    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        new Thread(() -> {
            lock.lock();
            try {
                TimeUnit.HOURS.sleep(1);
                num += 1000;
                System.out.println("A 线程执行了1秒,num = " + num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "A").start();

        for (int i = 0; i < 100; i++) {
            int temp = i;
            new Thread(() -> {
                lock.lock();
                try {
                    Thread.sleep(500);
                    num += 500;
                    System.out.println("B_" + temp + " 线程执行了0.5秒,num = " + num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }, "B_" + i).start();
        }
    }
}


