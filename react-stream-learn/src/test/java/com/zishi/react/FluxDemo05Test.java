package com.zishi.react;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/**
 * 异步多线程创建 Flux create方法
 * <p>
 * 异步单线程创建 Flux push 方法
 */
public class FluxDemo05Test {

    /**
     * Asynchronous and Multi-threaded: create
     *
     * @throws IOException
     */
    @Test
    void testFluxCreate01() {

        /**
         * 这样的用法和Flux.just其实没有太大的区别，在创建的时候就已经确定了怎么去下发元素。
         */
        Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::println);
    }

    FluxSink<String> outSink;

    @Test
    public void testFluxCreate02() {
        Flux<String> f = Flux.create(new Consumer<FluxSink<String>>() {
            @Override
            public void accept(FluxSink<String> sink) {
                outSink = sink;
            }
        });
        f.subscribe(System.out::println);
        //do something

        //下发元素, 调用者编写的逻辑，调用者可以手动停止发射元素
        // 这样做破坏了封装性，如果其他人使用不当，比如提前结束FluxSink等等会引发异常的Bug。
        for (int i = 0; i < 10; i++) {
            outSink.next(Thread.currentThread().getName() + ", 我来了: " + i);
            if (i == 3) {
                outSink.complete();
            }
        }
    }

    /**
     * 官网的例子
     */
    @Test
    void testFluxCreate03() {
        MyEventProcessor<String> eventProcessor = new MyEventProcessor<>();
        Flux<String> bridge = Flux.create(sink -> {
            eventProcessor.register(
                    new MyEventListener<String>() {

                        public void onDataChunk(List<String> chunk) {
                            System.out.println("................ onDataChunk ");
                            for (String s : chunk) {
                                sink.next(s);
                            }
                        }

                        public void processComplete() {
                            System.out.println("............. processComplete");
                            sink.complete();
                        }
                    });
        });

        bridge.subscribe(System.out::println);
    }

    /**
     * OverflowStrategy: 背压处理的枚举类
     * IGNORE: 完全忽略下游的背压请求，当下游队列满的时候这可能产生 IllegalStateException
     * ERROR： 当下游不能跟得上（上游发送信号的速度）的时候抛出 IllegalStateException
     * DROP： 当下游没有准备好接收信号的时候，丢弃掉
     * LATEST： 下游只从上游获取最新的信号
     * BUFFER： 当下游不能跟得上（上游发送信号的速度）的时候缓存所有的信号，注意：缓存无解流可能导致OutOfMemoryError
     *
     */
    @Test
    void testFluxCreate03_02() {
        MyEventProcessor<String> eventProcessor = new MyEventProcessor<>();
        Flux<String> bridge = Flux.create(sink -> {
            eventProcessor.register(
                    new MyEventListener<String>() {

                        public void onDataChunk(List<String> chunk) {
                            System.out.println("................ onDataChunk ");
                            for (String s : chunk) {
                                sink.next(s);
                            }
                        }

                        public void processComplete() {
                            System.out.println("............. processComplete");
                            sink.complete();
                        }
                    });
        }, FluxSink.OverflowStrategy.DROP);

        bridge.subscribe(System.out::println);
    }


    Consumer<String> producer;

    /**
     * testFluxCreate03 简化之后的结果
     */
    @Test
    void testFluxCreate04() {
        Flux.create(sink -> {
            producer = sink::next;
        }).subscribe(System.out::println);

        //do something

        //下发元素
        producer.accept("我来了");

        producer.accept("我来了2");

    }


    @Test
    void testFluxCreate00() throws IOException {

        /**
         * Flux.create:异步多线程的创建Flux的方法
         * 1. 通过FluxSink，以同步或者异步的方式发出多个元素
         * 2. 包括从多个线程发出元素
         */
        Flux<String> flux = Flux.create(new Consumer<FluxSink<String>>() {

            /**
             * FluxSink:
             * 有 next, error, complete方法
             * @param sink the input argument
             */
            @Override
            public void accept(FluxSink<String> sink) {
                new Thread() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 100; i++) {

                            try {
                                sink.next(Thread.currentThread().getName() + ", Hello: " + i / 0);
                            } catch (Exception e) {
                                try {
                                    Thread.sleep(5);
                                } catch (InterruptedException ex) {

                                }
                                // 所有通过sink发出的数据都会结束
                                sink.error(new Throwable("11111111111111111111"));
                                //sink.complete();
                            }

                        }


                    }
                }.start();

                new Thread() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 1000; i++) {
                            sink.next(Thread.currentThread().getName() + ", xxxxxxxxxxxx: " + i);
                        }
                    }
                }.start();
            }
        });

        flux.subscribe(System.out::println, throwable -> System.out.println(throwable.getMessage()));

        //System.in.read();
    }

}
























