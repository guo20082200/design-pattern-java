package org.example.jucdemo2.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 参考：
 * https://zhuanlan.zhihu.com/p/263762343
 * 优点：
 * 减少上下文切换：由于线程在等待锁时不会进入阻塞状态，因此减少了线程上下文切换的开销。
 * 适用于短时间等待：对于锁被持有时间较短的场景，自旋锁的效率较高。
 *
 * 自旋锁的优点
 * 自旋锁不会使线程状态发生切换，一直处于用户态，即线程一直都是active的；不会使线程进入阻塞状态，减少了不必要的上下文切换，执行速度快
 * 非自旋锁在获取不到锁的时候会进入阻塞状态，从而进入内核态，当获取到锁的时候需要从内核态恢复，需要线程上下文切换。
 * （线程被阻塞后便进入内核（Linux）调度状态，这个会导致系统在用户态与内核态之间来回切换，严重影响锁的性能）
 *
 * 缺点：
 * CPU资源浪费：如果锁被长时间持有，自旋的线程会浪费CPU资源。
 * 自旋策略的选择：需要选择合适的自旋策略，避免过度自旋导致的性能问题。
 */
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " come in...");
        while (!atomicReference.compareAndSet(null, thread)) {
        }
    }

    public void myUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + " come out...");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        }, "Thread1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        }, "Thread2").start();
    }
}

/**
总结
自旋锁：线程获取锁的时候，如果锁被其他线程持有，则当前线程将循环等待，直到获取到锁。
自旋锁等待期间，线程的状态不会改变，线程一直是用户态并且是活动的(active)。
自旋锁如果持有锁的时间太长，则会导致其它等待获取锁的线程耗尽CPU。
自旋锁本身无法保证公平性，同时也无法保证可重入性。
基于自旋锁，可以实现具备公平性和可重入性质的锁
 */




