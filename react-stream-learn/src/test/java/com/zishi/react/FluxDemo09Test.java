package com.zishi.react;

import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.atomic.LongAdder;


/**
 * Handling Errors
 * <p>
 * Catch and return a static default value.
 * <p>
 * Catch and execute an alternative path with a fallback method.
 * <p>
 * Catch and dynamically compute a fallback value.
 * <p>
 * Catch, wrap to a BusinessException, and re-throw.
 * <p>
 * Catch, log an error-specific message, and re-throw.
 * <p>
 * Use the finally block to clean up resources or a Java 7 “try-with-resource” construct.
 */
public class FluxDemo09Test {

    @Test
    void testHandlingErrors01() throws IOException {
        Flux<String> flux = Flux.just(1, 2, 0, 3, 4, 5)
                .map(i -> "100 / " + i + " = " + (100 / i)) //this triggers an error with 0
                .onErrorReturn("Divided by zero :(");// error handling example


        flux.subscribe(System.out::println);
    }

    @Test
    void testHandlingErrors02() throws IOException {
        Flux<String> flux = Flux.just(1, 2, 0, 3, 4, 5)
                .map(i -> "100 / " + i + " = " + (100 / i)) //this triggers an error with 0
                .onErrorReturn("Divided by zero :(");// error handling example


        flux.subscribe(System.out::println);
    }


    /**
     * try {
     * for (int i = 1; i < 11; i++) {
     * String v1 = doSomethingDangerous(i);
     * String v2 = doSecondTransform(v1);
     * System.out.println("RECEIVED " + v2);
     * }
     * } catch (Throwable t) {
     * System.err.println("CAUGHT " + t);
     * }
     */
    @Test
    void test01() throws IOException {

        Flux<String> s = Flux.just(1, 2, 0, 3, 4, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .map(v -> "xxxxxxxxxxxx" + v);
        s.subscribe(value -> System.out.println("RECEIVED " + value), error -> System.err.println("CAUGHT " + error));
    }


    /**
     * try {
     * return doSomethingDangerous(10);
     * }
     * catch (Throwable error) {
     * return "RECOVERED";
     * }
     */
    @Test
    void test02() throws IOException {

        Flux<String> flux = Flux.just(1, 2, 0, 3, 4, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorReturn("RECOVEREDooooo");
        flux.subscribe(value -> System.out.println("RECEIVED " + value), error -> System.err.println("CAUGHT " + error));
    }


    /**
     * Catch and swallow the error
     * 捕获并吐掉异常
     */
    @Test
    void test03() throws IOException {

        Flux<String> flux = Flux.just(1, 2, 0, 3, 4, 5)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorComplete();
        // TODO： 这里不会打印出来 CAUGHT 语句
        flux.subscribe(value -> System.out.println("RECEIVED " + value), error -> System.err.println("CAUGHT " + error));
    }

    /**
     * Fallback Method：
     * <p>
     * 例如：
     * String v1;
     * try {
     * v1 = callExternalService("key1");
     * }
     * catch (Throwable error) {
     * v1 = getFromCache("key1");
     * }
     * <p>
     * String v2;
     * try {
     * v2 = callExternalService("key2");
     * }
     * catch (Throwable error) {
     * v2 = getFromCache("key2");
     * }
     */
    @Test
    void test04() throws IOException {
        // 这里使用 flatMap
        //Flux.just("key1", "key2").flatMap(k -> callExternalService(k).onErrorResume(e -> getFromCache(k)));
    }


    /**
     * 类似 onErrorReturn， onErrorResume 有很多变体，可以让你过滤（是基于异常类或者Predicate）出哪一个异常来执行fall back，
     * <p>
     * The fact that it takes a Function also lets you choose a different fallback sequence to switch to, depending on the error encountered.
     * 下面的代码所示：
     *
     * @throws IOException
     */
    @Test
    void test05() throws IOException {
        /*Flux.just("timeout1", "unknown", "key2")
                .flatMap(k -> callExternalService(k)
                        .onErrorResume(error -> {
                            if (error instanceof TimeoutException)
                                return getFromCache(k);
                            else if (error instanceof UnknownKeyException)
                                return registerNewEntry(k, "DEFAULT");
                            else
                                return Flux.error(error);
                        })
                );*/
    }

    /**
     * 捕获并重新抛出异常
     * <p>
     * try {
     * return callExternalService(k);
     * }
     * catch (Throwable error) {
     * throw new BusinessException("oops, SLA exceeded", error);
     * }
     */
    @Test
    void test06() throws IOException {

        // In the “fallback method” example, the last line inside the flatMap gives us a hint at achieving the same reactively, as follows:
        //
        Flux<?> timeout1 = Flux.just("timeout1")
                .flatMap(k -> callExternalService(k))
                .onErrorResume(original -> Flux.error(new BusinessException("oops, SLA exceeded", original)));

        timeout1.subscribe(System.out::println);

        // However, there is a more straightforward way of achieving the same effect with onErrorMap:
        Flux<?> timeout11 = Flux.just("timeout1")
                .flatMap(k -> callExternalService(k))
                .onErrorMap(original -> new BusinessException("oops, SLA exceeded", original));

        timeout11.subscribe(System.out::println);
    }

    private Flux<?> callExternalService(String k) {
        return Flux.just(1, 2, 3, 4, 5);
    }

    /**
     * Log or React on the Side
     * 示例代码：
     * <p>
     * try {
     * return callExternalService(k);
     * }
     * catch (RuntimeException error) {
     * //make a record of the error
     * log("uh oh, falling back, service failed for key " + k);
     * throw error;
     * }
     */
    @Test
    void test07() throws IOException {
        LongAdder failureStat = new LongAdder();
        /*Flux<String> flux =
                Flux.just("unknown")
                        .flatMap(k -> callExternalService(k)
                                .doOnError(e -> {
                                    failureStat.increment();
                                    log("uh oh, falling back, service failed for key " + k);
                                })

                        );*/

        //flux.subscribe(System.out::println);
    }

    /**
     * Using Resources and the Finally Block
     * <p>
     * try (SomeAutoCloseable disposableInstance = new SomeAutoCloseable()) {
     * return disposableInstance.toString();
     * }
     * finally {
     * stats.stopTimerAndRecordTiming();
     * }
     */
    @Test
    void test08() throws IOException {

        LongAdder statsCancel = new LongAdder();

        Flux<String> flux =
                Flux.just("foo", "bar")
                        .doOnSubscribe(subscription -> subscription.request(2))
                        .doFinally(type -> {
                            System.out.println("发生异常后，finally的处理");
                            if (type == SignalType.CANCEL) {
                                statsCancel.increment();
                            }

                        })
                        .take(1);

        flux.subscribe(System.out::println);
    }

    @Test
    void testInterval() throws IOException {
        Flux.interval(Duration.ofMillis(250)).subscribe(System.out::println);
        System.in.read();
    }



    @Test
    void testRetry() {
        Flux.interval(Duration.ofMillis(100))
                .map(input -> {
                    if (input < 2) {
                        System.out.println("input :" + input);
                        return "tick " + input;
                    }
                    throw new RuntimeException("boom");
                })
                .retry(10)
                .elapsed()
                .subscribe(System.out::println, System.err::println);

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handling Exceptions in Operators or Functions
     */
    @Test
    void test09() throws IOException {
        Flux<String> converted = Flux
                .range(1, 10)
                .map(i -> {
                    try { return convert(i); }
                    catch (IOException e) { throw Exceptions.propagate(e); }
                });

        converted.subscribe(
                v -> System.out.println("RECEIVED: " + v),
                e -> {
                    if (Exceptions.unwrap(e) instanceof IOException) {
                        System.out.println("Something bad happened with I/O");
                    } else {
                        System.out.println("Something bad happened");
                    }
                }
        );
    }

    public String convert(int i) throws IOException {
        if (i > 3) {
            throw new IOException("boom " + i);
        }
        return "OK " + i;
    }
}
























