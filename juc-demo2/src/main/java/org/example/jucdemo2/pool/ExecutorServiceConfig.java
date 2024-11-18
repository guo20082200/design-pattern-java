package org.example.jucdemo2.pool;

import java.util.concurrent.*;

/**
 * @author zishi
 */
public class ExecutorServiceConfig {


    public static final RejectedExecutionHandler DEFAULT_HANDLER = new ThreadPoolExecutor.AbortPolicy();


    /**
     * 创建固定大小的线程池:
     * final ExecutorService executor = Executors.newFixedThreadPool(1);
     * final ExecutorService executor = Executors.newSingleThreadExecutor();
     * final ExecutorService executor = Executors.newCachedThreadPool();
     */
    //final ExecutorService executor = Executors.newFixedThreadPool(1);

    public static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1, 1, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1),
            Executors.defaultThreadFactory(), DEFAULT_HANDLER);
}
