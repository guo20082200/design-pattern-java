package com.zishi.react;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;

public class FluxDemo03Test {

    @Test
    void testSubscribe01() throws IOException {
        Flux.range(1, 100)
                .log().subscribe(
                        System.out::println,  // subscribe
                        System.out::println,  // error handler
                        () -> {
                        },             // onComplete
                        s -> s.request(10) // subscription request
                );
    }

    @Test
    void testDelayElements() throws IOException {

        // delay每一个Flux里面的元素
        Disposable disposable = Flux.range(1, 100)
                .log()
                .delayElements(Duration.ofMillis(100))
                .subscribe(System.out::println, System.out::println, () -> {
                }, s -> s.request(200));

        int read = System.in.read(); // 阻塞
        System.out.println(read);
    }

    @Test
    void testLimitRate() throws IOException {

        Flux.range(1, 1000)
                .log()
                .limitRate(100)
                .delayElements(Duration.ofMillis(10))
                .subscribe(System.out::println);

        int read = System.in.read(); // 阻塞

        /**
         * 输出结果如下：
         * [ INFO] (main) | onSubscribe([Synchronous Fuseable] FluxRange.RangeSubscription)
         * [ INFO] (main) | request(100)
         * [ INFO] (main) | onNext(1)
         * [ INFO] (main) | onNext(2)
         * ....
         * [ INFO] (main) | onNext(8)
         * [ INFO] (main) | onNext(9)
         * [ INFO] (main) | onNext(100)
         * 1
         * 2
         * ....
         * 74
         * [ INFO] (parallel-7) | request(75)
         * ....
         *
         */


    }


    /**
     * @throws IOException
     */
    @Test
    void testHighAndLowTide() throws IOException {
        Flux.range(1, 1000)
                .log()
                // lowTide指出预获取操作的补充优化的值，即修改75%的默认值；highTide指出预获取数量。
                .limitRate(10, 2)
                .delayElements(Duration.ofMillis(500))
                .subscribe(System.out::println);

        int read = System.in.read(); // 阻塞
    }

    @Test
    void testLimitRequest() throws IOException {
        Flux<Integer> integerFlux = Flux.range(0, 100);
        // 最后，来看一些Flux提供的预获取方法：
        // 指出预取数量
        integerFlux.limitRate(10);
        // lowTide指出预获取操作的补充优化的值，即修改75%的默认值；highTide指出预获取数量。
        integerFlux.limitRate(10, 15);
        // 哎～最典型的就是，请求无数:request(Long.MAX_VALUE)但是我给你limitRate(2)；那你也只能乖乖每次得到两个哈哈哈哈！
        // 还有一个就是limitRequest(N)，它会把下流总请求限制为N。如果下流请求超过了N，那么只返回N个，否则返回实际数量。然后认为请求完成，向下流发送onComplete信号。
        integerFlux.limitRequest(5).subscribe(new BaseSubscriber<Integer>() {

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("开始读取...");
                System.out.println(value);
                // 指出下一次读取多少个
                request(2);

            }

            @Override
            protected void hookOnSubscribe(Subscription subscription) {

                System.out.println("开始啦！");
                // 记得至少请求一次，否则不会执行hookOnNext()方法
                request(1);
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("结束啦！");
            }
        });
        // 上面这个只会输出5个。
    }


}






















