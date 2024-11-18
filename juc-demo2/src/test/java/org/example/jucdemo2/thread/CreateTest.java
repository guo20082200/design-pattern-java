package org.example.jucdemo2.thread;

import org.junit.jupiter.api.Test;

public class CreateTest {

    @Test
    void test01() {
        MyThread thread = new MyThread();
        thread.start();

        Thread thread2 = new MyThread();
        thread2.start();

        System.out.println(Thread.currentThread().getName());
    }


    private static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread()
                        .getName() + ": " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Test
    void test02() {

        MyThreadRunner threadRunnable = new MyThreadRunner();
        Thread thread = new Thread(threadRunnable);
        thread.start();

        MyThreadRunner threadRunnable2 = new MyThreadRunner();
        Thread thread2 = new Thread(threadRunnable2);

        thread2.start();

        System.out.println(Thread.currentThread().getName());

    }

    private static class MyThreadRunner implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread()
                        .getName() + ": " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
