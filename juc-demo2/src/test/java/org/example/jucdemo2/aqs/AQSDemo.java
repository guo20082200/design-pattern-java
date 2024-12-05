package org.example.jucdemo2.aqs;

import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo {
    private static int num;


    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000);
                num += 1000;
                System.out.println("A 线程执行了1秒,num = " + num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "A").start();

        new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(500);
                num += 500;
                System.out.println("B 线程执行了0.5秒,num = " + num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "B").start();

        new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(100);
                num += 100;
                System.out.println("C 线程执行了0.1秒,num = " + num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "C").start();
    }
}


