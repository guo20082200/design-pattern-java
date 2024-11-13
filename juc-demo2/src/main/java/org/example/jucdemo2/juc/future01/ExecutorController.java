package org.example.jucdemo2.juc.future01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zishi
 */
@RestController
@RequestMapping("/executor")
public class ExecutorController {

    private static final RejectedExecutionHandler DEFAULT_HANDLER = new ThreadPoolExecutor.AbortPolicy();


    /**
     * 创建固定大小的线程池:
     * final ExecutorService executor = Executors.newFixedThreadPool(1);
     * final ExecutorService executor = Executors.newSingleThreadExecutor();
     * final ExecutorService executor = Executors.newCachedThreadPool();
     */
    final ExecutorService executor = Executors.newFixedThreadPool(1);

    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1, 1, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1),
            Executors.defaultThreadFactory(), DEFAULT_HANDLER);

    final ReentrantLock lock = new ReentrantLock();

    @GetMapping("/test")
    public Object test() throws InterruptedException, ExecutionException {
        // 当前已经提交执行的任务不进入线程池内部的队列
        // 没有执行的任务进入队列
        // 队列满的时候执行线程池的拒绝策略
        //EXECUTOR.execute(new Task("xxxx" + System.currentTimeMillis()));
        Future<?> submit = EXECUTOR.submit(new Task2());

        /*
         *  isDone() 方法来判断任务是否完成，如果任务完成，返回true
         * 完成可能是由于：线程正常的结束，线程异常结束，或者线程取消，所有的情况都返回true
         */
        boolean done = submit.isDone();
        if (done) {
            // 等待线程结束，并获取线程的返回结果，阻塞
            Object o = submit.get();
            System.out.println(".........." + o);
        }

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

class Task2 implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "hello" + new Date();
    }
}
