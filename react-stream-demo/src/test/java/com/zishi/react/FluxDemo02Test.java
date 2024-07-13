package com.zishi.react;

import com.beust.jcommander.internal.Lists;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.util.List;
import java.util.function.Supplier;

public class FluxDemo02Test {

    /**
     * 最基本的buffer, 把Flux产生的全部元素放到一个List里面,当onComplete的时候返回这个List, 消费者直接消费这个List.
     */
    @Test
    void testBuffer01() {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);
        Flux<List<Integer>> buffer = flux.buffer();
        buffer.subscribe(integers -> {
            integers.forEach(System.out::println);
        });
    }

    /**
     * buffer(int maxSize)
     */
    @Test
    void testBuffer02() {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);
        // maxSize： 最大收集的元素的个数
        Flux<List<Integer>> buffer = flux.buffer(2);
        buffer.subscribe(integers -> {
            System.out.println(integers);
            integers.forEach(System.out::println);
        });

        System.out.println(".......................");
        Flux<List<Integer>> buffer2 = flux.buffer(4);
        //integers.forEach(System.out::println);
        buffer2.subscribe(System.out::println);
        /**
         * 结果如下：
         * [1, 2, 3, 4]
         * [5, 6]
         */
    }

    /**
     * buffer(int maxSize, Supplier<C> bufferSupplier)
     */
    @Test
    void testBuffer03() {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);
        Flux<List<Integer>> buffer = flux.buffer(7, new Supplier<List<Integer>>() {
            @Override
            public List<Integer> get() {
                return Lists.newArrayList(10, 33);
            }
        });

        buffer.subscribe(System.out::println);
        /*
        结果如下：
        [10, 33, 1, 2, 3, 4, 5]
        [10, 33, 6]
         */
    }


    /**
     * buffer(int maxSize, int skip):
     * 1. maxSize < skip:这时候会丢弃多余的元素
     * 2. maxSize > skip:这时候会重复消费
     * skip参数指定经过多少个产品开始进行buffer. 每过skip个产品,开始装载maxsize个产品后发送给消费者
     */
    @Test
    void testBuffer04() {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);
        Flux<List<Integer>> buffer = flux.buffer(2, 3);
        buffer.subscribe(integers -> {
            System.out.println("产品到达,进行卸货:" + integers);
            integers.stream().forEach(integer -> {
                System.out.println(integer);
            });
        });

        /**
         * 结果如下：
         * 产品到达,进行卸货:[1, 2]
         * 1
         * 2
         * 产品到达,进行卸货:[4, 5]
         * 4
         * 5
         */


        Flux<List<Integer>> buffer2 = flux.buffer(4, 3);
        buffer2.subscribe(integers -> {
            System.out.println("产品到达,进行卸货:" + integers);
            integers.stream().forEach(integer -> {
                System.out.println(integer);
            });
        });

        /**
         * 结果如下：
         * 产品到达,进行卸货:[1, 2, 3, 4]
         * 1
         * 2
         * 3
         * 4
         * 产品到达,进行卸货:[4, 5, 6]
         * 4
         * 5
         * 6
         */

    }


    /**
     * buffer(int maxSize,int skip, Supplier<C> bufferSupplier)
     */
    @Test
    void testBuffer05() {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);
        Flux<List<Integer>> buffer = flux.buffer(10, 3, () -> Lists.newArrayList(10, 33, 100, 12, 13));
        buffer.subscribe(integers -> {
            System.out.println("产品到达,进行卸货:" + integers);
            integers.forEach(System.out::println);
        });
    }


    @Test
    void testBuffer06() {
        Flux<Integer> flux = Flux.range(1, 20);
        Flux<List<Integer>> buffer = flux.buffer(3);

        buffer.subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("FluxDemo02Test.hookOnSubscribe");
                request(1);
            }

            @Override
            protected void hookOnNext(List<Integer> value) {

                System.out.println("received " + value);
                request(1);
            }

            @Override
            protected void hookOnComplete() {

                System.out.println("FluxDemo02Test.hookOnComplete");
            }


            @Override
            protected void hookFinally(SignalType type) {
                System.out.println("FluxDemo02Test.hookFinally");
            }
        });

    }




}






















