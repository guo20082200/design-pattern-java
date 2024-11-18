package org.example.jucdemo2.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁和非公平锁
 */
public class UnfairLockTest {

    @Test
    void testTicket() {
        Ticket ticket = new Ticket();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i<55; i++) {
                    ticket.sale();
                }
            }
        }, "a").start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i<55; i++) {
                    ticket.sale();
                }
            }
        }, "b").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i<55; i++) {
                    ticket.sale();
                }
            }
        }, "c").start();
    }
}


class Ticket {
    public int number = 50;

    private final ReentrantLock lock = new ReentrantLock(true);

    public void sale() {
        lock.lock();
        try {
            if(number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第"+ (number--) + ", 还剩下：" + number);
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }
}