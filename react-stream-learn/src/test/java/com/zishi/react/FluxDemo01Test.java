package com.zishi.react;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class FluxDemo01Test {

    @Test
    void testCreate() {
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);

        Mono<String> noData = Mono.empty();

        Mono<String> data = Mono.just("foo");

        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
    }

    @Test
    void testSubscribe() throws IOException {

        Flux<String> just = Flux.just("foo", "bar", "foobar");

        // public final Disposable subscribe(Consumer<? super T> consumer)
        just.subscribe(System.out::println);


        // public final void subscribe(Subscriber<? super T> actual)
        just.subscribe(new Subscriber<>() {
            Subscription sub;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("FluxDemoTest.onSubscribe");
                sub = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(String value) {
                System.out.println("FluxDemoTest.onNext : " + value);
                sub.request(1);
            }

            @Override
            public void onError(Throwable t) {

                System.out.println("FluxDemoTest.onError");
            }

            @Override
            public void onComplete() {
                System.out.println("FluxDemoTest.onComplete");
            }
        });

        // CoreSubscriber: 比 Subscriber 多了一个currentContext的方法
        just.subscribe(new CoreSubscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

        Flux<Integer> just02 = Flux.just(1, 2, 0, 3).map(e -> 10 / e);
        /**
         * public final Disposable subscribe(@Nullable Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer)
         * 1. 正常的Consumer
         * 2. 处理异常数据的 Consumer
         */
        just02.subscribe(System.out::println, ex -> System.out.println(Arrays.toString(ex.getStackTrace())));


        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(System.out::println, error -> System.err.println("Error: " + error)); // Error: java.lang.RuntimeException: Got to 4


        /*just02.subscribe(System.out::println, ex -> System.out.println(Arrays.toString(ex.getStackTrace())), new Runnable() {
            @Override
            public void run() {

                System.out.println("....... Runnable");
            }
        });*/

        /*
         * subscribe(Consumer<? super T> consumer,
         *           Consumer<? super Throwable> errorConsumer,
         *           Runnable completeConsumer,
         *           Consumer<? super Subscription> subscriptionConsumer);
         */
        just02.subscribe(System.out::println, ex -> System.out.println(Arrays.toString(ex.getStackTrace())), new Runnable() {
            @Override
            public void run() {

                System.out.println("....... Runnable");
            }
        }, new Consumer<Subscription>() {
            @Override
            public void accept(Subscription subscription) {
                System.out.println(".......llllllllllllllllllllllllllllll");
                subscription.cancel();
            }
        });

        Flux<Integer> ints2 = Flux.range(1, 4);
        ints2.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done")); //Subscribe with a Subscriber that includes a handler for completion events.

    }

    @Test
    void testDispose() throws IOException, InterruptedException {
        Flux<Long> longFlux = Flux.interval(Duration.ofMillis(10));
        //take方法准确获取订阅数据量
        Disposable disposable = longFlux.take(100).subscribe(x -> System.out.println("->{}: " + x));
        //不能停止正在推送数据中的Flux或Mono流
        Thread.sleep(100);
        //彻底停止正在推送数据中的Flux或Mono流
        //disposable.dispose();
        System.out.println("->Stop");
        Disposable disposable2 = longFlux.take(100).subscribe(x -> System.out.println("----------------------->{}: " + x));
        Thread.sleep(100);
        //Disposable disposed = Disposables.disposed();
        //Disposable.Swap swap = Disposables.swap();
        Disposable.Composite composite = Disposables.composite(disposable, disposable2);
        composite.dispose(); // 可以取消多个订阅
        //int read = System.in.read();

    }

    @Test
    void testBaseSubscriber() throws IOException, InterruptedException {
        SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
        Flux<Integer> integerFlux = Flux.range(1, 4);
        integerFlux.subscribe(ss);
    }

    //On Backpressure and Ways to Reshape Requests
    @Test
    void testBackpressure() throws IOException, InterruptedException {

        Flux.range(1, 10)
                .doOnRequest(r -> System.out.println("request of " + r))
                .subscribe(new BaseSubscriber<Integer>() {

                    @Override
                    public void hookOnSubscribe(Subscription subscription) {
                        request(1);
                    }

                    @Override
                    public void hookOnNext(Integer integer) {
                        System.out.println("Cancelling after having received " + integer);
                        cancel();
                    }
                });
    }

    // Operators that Change the Demand from Downstream
    @Test
    void testReshaped () throws IOException, InterruptedException {

    }

}






















