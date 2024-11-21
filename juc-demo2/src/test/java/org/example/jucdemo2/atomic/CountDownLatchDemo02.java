package org.example.jucdemo2.atomic;


import lombok.Data;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CountDownLatchDemo02 {
    private static final int N = 50;
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(N);
        Stu stu = new Stu();

        for (int i = 0; i < N; i++) {
            new Thread(() -> {
                try {
                    for (int i1 = 0; i1 < 1000; i1++) {
                        stu.addOne();
                    }
                }finally {
                    startSignal.countDown();
                }
            }, "aaa").start();
        }

        startSignal.await();
        System.out.println(stu.atomicInteger.get());
    }
}

@Data
class Stu {
    public AtomicInteger atomicInteger = new AtomicInteger();

    public void addOne() {
        atomicInteger.incrementAndGet();
    }
}

