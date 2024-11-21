package org.example.jucdemo2.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo03 {

    public static void main(String[] args) throws InterruptedException {


        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 4; i++) {
            new Thread(new MyRun(countDownLatch)).start();
        }

        System.out.println(".................");
        TimeUnit.SECONDS.sleep(4);
        countDownLatch.countDown();
    }
}


class MyRun implements Runnable {

    public MyRun(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    private final CountDownLatch countDownLatch;

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " start..");
            countDownLatch.await();
            System.out.println("3333");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

