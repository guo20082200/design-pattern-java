package org.example.jucdemo2.atomic;

import com.google.common.base.Stopwatch;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * synchronized 和 AtomicInteger 和 LongAdder 和 LongAccumulator 对比
 *
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
                        b.addOne0();
                        //b.addOne1();
                        //b.addOne2();
                        //b.addOne3();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, "b").start();
        }

        countDownLatch.await();
        Duration elapsed = started.elapsed();
        System.out.println(b.num); // 100000000
        System.out.println("synchronized:" + elapsed.getNano()/1000000); // synchronized:697

        //System.out.println(b.atomicInteger.get()); // 100000000
        //System.out.println("atomicInteger: " + elapsed.getNano()/1000000); //atomicInteger:  926

        //System.out.println(b.adder.sum()); //100000000
        //System.out.println("adder:" + elapsed.getNano()/1000000); // adder:213


        //System.out.println(b.accumulator.longValue()); //100000000
        //System.out.println("accumulator: " + elapsed.getNano()/1000000); //accumulator:  174 ms
    }

}

class B {
    public int num = 0;
    public AtomicInteger atomicInteger = new AtomicInteger(0);
    public LongAdder adder = new LongAdder();
    public LongAccumulator accumulator = new LongAccumulator(Long::sum, 0);

    public synchronized void addOne0() {
        num++;
    }

    public void addOne1() {
        atomicInteger.incrementAndGet();
    }

    public void addOne2() {
        adder.increment();
    }

    public void addOne3() {
        accumulator.accumulate(1);
    }

}