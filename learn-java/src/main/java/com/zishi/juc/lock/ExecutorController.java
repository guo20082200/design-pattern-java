package com.zishi.juc.lock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zishi
 */
@RestController
@RequestMapping("/executor")
public class ExecutorController {

    /**
     * 创建固定大小的线程池:
     */
    final ExecutorService executor = Executors.newFixedThreadPool(1);
    final ReentrantLock lock = new ReentrantLock();

    @GetMapping("/test")
    public Object test() throws InterruptedException {
        executor.execute(new Task("xxxx" + System.currentTimeMillis()));
        return Map.of("a", "1");
    }
}

class Task implements Runnable {
    private final String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("start task " + name);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("end task " + name);
    }
}
