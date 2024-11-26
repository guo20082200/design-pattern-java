package org.example.jucdemo2.atomic;

import com.google.common.base.Stopwatch;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder
 */
public class LongAdderDemo02Test {

    static int threadNum = 100;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        B b = new B();

        Stopwatch started = Stopwatch.createStarted();
        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                try {
                    for (int i1 = 0; i1 < 1000000; i1++) {
                        //b.addOne();
                        b.addOne2();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, "b").start();
        }

        countDownLatch.await();
        Duration elapsed = started.elapsed();
        //System.out.println(b.atomicInteger.get()); // 100000000
        //System.out.println(elapsed.getNano()); // 695510800

        System.out.println(b.adder.sum()); //100000000
        System.out.println(elapsed.getNano()); // 255769600
    }

}

class B {
    public LongAdder adder = new LongAdder();
    public AtomicInteger atomicInteger = new AtomicInteger(0);

    public void addOne() {
        atomicInteger.incrementAndGet();
    }

    public void addOne2() {
        adder.increment();
    }

}