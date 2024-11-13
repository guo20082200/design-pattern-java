package com.zishi.juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock比直接使用synchronized更安全，可以替代synchronized进行线程同步。
 * 但是，synchronized可以配合wait和notify实现线程在条件不满足时等待，条件满足时唤醒，用ReentrantLock我们怎么编写wait和notify的功能呢？
 * 答案是使用Condition对象来实现wait和notify的功能。
 * 我们仍然以TaskQueue为例，把前面用synchronized实现的功能通过ReentrantLock和Condition来实现：
 *
 *
 * Condition提供的await()、signal()、signalAll()原理和
 * synchronized锁对象的wait()、notify()、notifyAll()是一致的，
 * 并且其行为也是一样的：
 *
 * @author zishi
 */
@RestController
@RequestMapping("/condition")
public class ConditionController {


    private static final Logger log = LoggerFactory.getLogger(ConditionController.class);
    final ReentrantLock lock = new ReentrantLock();


    @GetMapping("/test")
    public Object test() throws InterruptedException {
        Condition condition = lock.newCondition();

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
