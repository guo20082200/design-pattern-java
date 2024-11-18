package org.example.jucdemo2.juc.lock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock可以替代synchronized进行同步；
 *
 * ReentrantLock获取锁更安全；
 *
 * 必须先获取到锁，再进入try {...}代码块，最后使用finally保证释放锁；
 *
 * 可以使用tryLock()尝试获取锁。
 * @author zishi
 */
@RestController
@RequestMapping("/reentrantLockDemo")
public class ReentrantLockDemoController {

    /**
     * 同一把锁，锁定的是线程访问同一个资源，和lock是不是属于同一个方法没有关系
      */
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


    @GetMapping("/test2")
    public Object test2() throws InterruptedException {

        assert !lock.isHeldByCurrentThread();
        System.out.println("start2");
        lock.lock();
        try {
            //TimeUnit.SECONDS
            System.out.println("................2" + Thread.currentThread().getName());
            Thread.sleep(10 * 1000);
        } finally {
            System.out.println("unlock2");
            lock.unlock();
        }
        System.out.println("end2");
        return Map.of("a2", "2");
    }

    @GetMapping("/test3")
    public Object test3() throws InterruptedException {

        assert !lock.isHeldByCurrentThread();
        System.out.println("start3");

        //如果当前锁没有被其他线程持有，并且在给定的等待时间内，并且当前线程没有被interrupted ，那么获取锁，
        boolean tryLock = lock.tryLock(2, TimeUnit.SECONDS);
        if(tryLock) {
            try{
                //TimeUnit.SECONDS
                System.out.println("................3" + Thread.currentThread().getName());
                Thread.sleep(10 * 1000);
            } finally {
                System.out.println("unlock3");
                lock.unlock();
            }
        }

        System.out.println("end3");
        return Map.of("a3", "3");
    }
}
