package com.zishi.react;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

/**
 * Threading and Schedulers
 */
public class FluxDemo08Test {

    @Test
    void testPublishOn() throws IOException {

        /**
         * Scheduler: 提供一个操作符的异步边界抽象
         *
         *
         * public static Scheduler newParallel(String name, int parallelism)
         * name： 线程的前缀
         * parallelism： 线程的个数
         *
         */
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 20)
                .log()
                .map(i -> Thread.currentThread() + " : " + 10 + i) // 这里是main线程
                // publishOn
                .publishOn(s)
                .log()
                .map(i -> "-------------------" + Thread.currentThread() + "value " + i + "==================="); // 这里是parallel-scheduler线程

        new Thread(() -> {
            flux.log().subscribe(System.out::println);
        }).start();

        System.in.read();

    }


    @Test
    void testSubscribeOn() throws IOException {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 10)
                .log()
                .map(i -> 10 + i)
                .log()
                .subscribeOn(s)
                .log()
                .map(i -> "value " + i)
                .log();

        new Thread(() -> flux.subscribe(System.out::println)).start();
        System.in.read();
    }

}
























