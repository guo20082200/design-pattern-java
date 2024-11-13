package org.example.jucdemo2.juc.lock;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock是一种可重入的互斥锁，提供公平锁和非公平锁的选择，支持中断和超时。
 * 其优点包括可中断、可设置超时、多条件绑定等。
 *
 * ReentrantLock 的使用注意事项，
 * 如避免锁粒度过大、保持加锁顺序防止死锁，并提供了实际应用案例，
 * 包括处理方法超时和演示公平锁与非公平锁的区别。
 *
 * @author zishi
 */
public class ReentrantLockDemo {


    final ReentrantLock lock = new ReentrantLock();

    @GetMapping("/test")
    public Object test() throws InterruptedException {

        assert !lock.isHeldByCurrentThread();
        System.out.println("start");
        lock.lock();
        try {
            //TimeUnit.SECONDS
            System.out.println("................" + Thread.currentThread().getName());
            Thread.sleep(10 * 1000);
        } finally {
            System.out.println("unlock");
            lock.unlock();
        }
        System.out.println("end");
        return Map.of("a", "1");
    }
}
