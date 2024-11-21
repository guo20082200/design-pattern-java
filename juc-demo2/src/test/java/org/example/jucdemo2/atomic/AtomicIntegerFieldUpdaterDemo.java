package org.example.jucdemo2.atomic;


import lombok.Data;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 参考： https://juejin.cn/post/6907610980428021773
 */
@Data
class BankCard {

    private String number = "xxx";
    private String account = "abc";
    private int balance;
    public volatile int volatileBalance;

    public BankCard(int balance) {
        this.balance = balance;
        this.atomicBalance = new AtomicLong(balance);
        this.volatileBalance = balance;
    }

    /**
     * 1.线程不安全
     *
     * @param number
     */
    public void deposit01(int number) {
        balance += number;
    }

    /**
     * 2. 线程安全的方法，但是效率低下
     *
     * @param number
     */
    public synchronized void deposit02(int number) {
        balance += number;
    }

    private AtomicLong atomicBalance;

    /**
     * 3. 使用 AtomicLong 修改
     *
     * @param number
     */
    public void deposit03(int number) {
        long andAdd = atomicBalance.getAndAdd(number);
    }

    private static final AtomicIntegerFieldUpdater<BankCard> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankCard.class, "volatileBalance");

    /**
     * 使用 AtomicIntegerFieldUpdater,修改volatile属性
     */
    public void deposit04(int number) {
        int andAdd = fieldUpdater.getAndAdd(this, number);
    }

}

public class AtomicIntegerFieldUpdaterDemo {

    static final int threadNumber = 50;


    /**
     * 1. 当前结果并不正确，是因为deposit方法不是线程安全的
     * 2. 修改deposit方法为线程安全即可
     *
     * @throws InterruptedException
     */
    static void test01() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        BankCard bankCard = new BankCard(0);
        for (int i = 0; i < threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int i1 = 0; i1 < 1000; i1++) {
                        bankCard.deposit01(1);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }

        countDownLatch.await();

        System.out.println(bankCard.getBalance());
    }

    /**
     * 2. 采用同步防范，保证线程安全，结果正确
     *
     * @throws InterruptedException
     */
    static void test02() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        BankCard bankCard = new BankCard(0);
        for (int i = 0; i < threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int i1 = 0; i1 < 1000; i1++) {
                        bankCard.deposit02(1);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println(bankCard.getBalance());
    }

    /**
     * 3. 采用原子类进行操作
     */
    static void test03() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        BankCard bankCard = new BankCard(0);
        for (int i = 0; i < threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int i1 = 0; i1 < 1000; i1++) {
                        bankCard.deposit03(1);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println(bankCard.getAtomicBalance().get());
    }

    /**
     * 4. 采用原子类进行操作
     */
    static void test04() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        BankCard bankCard = new BankCard(0);
        for (int i = 0; i < threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int i1 = 0; i1 < 1000; i1++) {
                        bankCard.deposit04(1);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println(bankCard.getVolatileBalance());
    }

    public static void main(String[] args) throws InterruptedException {

        //test01();
        /*for (int i = 0; i < 10; i++) {
            test02();
        }*/
        /*for (int i = 0; i < 10; i++) {
            test03();
        }*/

        for (int i = 0; i < 10; i++) {
            test04();
        }

        // AtomicIntegerFieldUpdater<BankCard> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankCard.class, "balance");

    }
}

/*
public class Test {

    private volatile int a = 0;
    private static final AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(Test.class, "a");

    public static void main(String[] args) {
        Test test = new Test();
        for (int i = 0; i < 200; i++) {
            final int j = i;
            Thread t = new Thread(() -> {
                System.out.println("i=" + j + ", a=" + updater.incrementAndGet(test));
            });
            t.start();
        }
    }
}*/
