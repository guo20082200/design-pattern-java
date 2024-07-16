package com.zishi.react;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;

/**
 * https://projectreactor.io/docs/core/release/reference/index.html#_simple_ways_to_create_a_flux_or_mono_and_subscribe_to_it
 * <p>
 * 程序来创建 Flux / Mono （onNext, onError, and onComplete）
 * <p>
 * 引入 sink
 */
public class FluxDemo04Test {

    /**
     * 同步创建 Synchronous generate
     *
     * @throws IOException
     */
    @Test
    void testSynchronousGenerate() throws IOException {
        Flux<String> flux = Flux.generate(
                () -> 3, // 这里提供了初始状态
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3 * state); // 上游发给下游的值
                    if (state == 10) sink.complete(); // 确定什么时候发送数据完成
                    return state + 1; // 下一次调用的时候返回的新的state的值
                });

        Flux<String> flux2 = Flux.generate(
                new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return 3;
                    }
                },
                new BiFunction<Integer, SynchronousSink<String>, Integer>() {
                    @Override
                    public Integer apply(Integer state, SynchronousSink<String> stringSynchronousSink) {

                        stringSynchronousSink.next("3 x " + state + " = " + 3 * state);
                        if (state == 10) {
                            stringSynchronousSink.complete();
                        }
                        return state + 1;
                    }
                });

        flux.subscribe(System.out::println);

        /**
         * 输出结果：
         * 3 x 0 = 0
         * 3 x 1 = 3
         * 3 x 2 = 6
         * 3 x 3 = 9
         * 3 x 4 = 12
         * 3 x 5 = 15
         * 3 x 6 = 18
         * 3 x 7 = 21
         * 3 x 8 = 24
         * 3 x 9 = 27
         * 3 x 10 = 30
         */
        flux.subscribe(System.out::println);
    }


    /**
     * @throws IOException
     */
    @Test
    void testSynchronousGenerateWithAtomicLong() throws IOException {
        Flux<String> flux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3 * i);
                    if (i == 10) sink.complete();
                    return state;
                });

        flux.subscribe(System.out::println);
        flux.subscribe(System.out::println);
    }


    /**
     *
     * 这里可以释放state的状态：
     *
     * 在这种情况下，state包含了一个数据库链接或者其他的资源，这些资源在程序的最后需要被处理，
     * 在Consumer的lambda表达式里面可以关闭数据库的链接，或者在程序结束的时候需要被处理完成的任何任务
     *
     * @throws IOException
     */
    @Test
    void testSynchronousGenerateWithAtomicLong2() throws IOException {
        Flux<String> flux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3 * i);
                    if (i == 10) sink.complete();
                    return state;
                }, (state) -> {
                    System.out.println("可以释放state的状态");
                    System.out.println("state: " + state);
                });

        flux.subscribe(System.out::println);
    }


}






















