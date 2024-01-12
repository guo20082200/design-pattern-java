package com.zishi.zk;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1. 一个线程等待其他多个线程都执行完毕，再继续自己的工作
 * 2. 多个线程等待某一个线程的信号，同时开始执行
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        //demo01();
        demo02();
    }

    /**
     * 主线程等待子线程执行完成在执行
     *
     */
    public static void demo01() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        final CountDownLatch latch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("子线程" + Thread.currentThread().getName() + "执行完成");
                        /**
                         * 递减锁存器的计数，如果计数到达零，则释放所有等待的线程。如果当前计数大于零，则将计数减少.
                         * 对计数器进行递减1操作，当计数器递减至0时，当前线程会去唤醒阻塞队列里的所有线程。
                         */
                        latch.countDown();//当前线程调用此方法，则计数减一
                        System.out.println(latch.getCount());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }

        try {
            System.out.println("主线程" + Thread.currentThread().getName() + "等待子线程执行完成...");
            latch.await();//阻塞当前线程，直到计数器的值为0
            System.out.println("主线程" + Thread.currentThread().getName() + "开始执行...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();
    }


    /**
     * 1. 等待所有的线程同时开始
     * 2. 等待所有的线程都执行完毕
     */
    public static void demo02() {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("选手" + Thread.currentThread().getName() + "正在等待裁判发布口令");
                        cdOrder.await();
                        System.out.println("选手" + Thread.currentThread().getName() + "已接受裁判口令");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("选手" + Thread.currentThread().getName() + "到达终点");
                        cdAnswer.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        try {
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("裁判" + Thread.currentThread().getName() + "即将发布口令");
            cdOrder.countDown();
            System.out.println("裁判" + Thread.currentThread().getName() + "已发送口令，正在等待所有选手到达终点");
            cdAnswer.await();
            System.out.println("所有选手都到达终点");
            System.out.println("裁判" + Thread.currentThread().getName() + "汇总成绩排名");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
