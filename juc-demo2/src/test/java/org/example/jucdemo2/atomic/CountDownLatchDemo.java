package org.example.jucdemo2.atomic;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
    private static final int N = 10;
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);
        // create and start threads
        for (int i = 0; i < N; ++i) {
            new Thread(new Worker(startSignal, doneSignal)).start();
        }
        doSomethingElse();            // don't let run yet
        System.out.println("...." + startSignal.getCount());
        startSignal.countDown();      // let all threads proceed
        System.out.println(">>>>>>" + startSignal.getCount());
        doSomethingElse();
        doneSignal.await();           // wait for all to finish
    }
    private static void doSomethingElse() {
        System.out.println("Driver.doSomethingElse");
    }
}


class Worker implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    public void run() {
        try {
            startSignal.await();
            doWork();
            TimeUnit.SECONDS.sleep(2);
            doneSignal.countDown();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    void doWork() {

        System.out.println("Worker.doWork");
    }
}
