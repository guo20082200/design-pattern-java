package com.zishi.react;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class Demo {
    static FluxSink<String> outSink;
    public static void main(String[] args) {
        init();
        //下发元素, 调用者编写的逻辑，调用者可以手动停止发射元素
        for (int i = 0; i < 10; i++) {
            outSink.next(Thread.currentThread().getName() + ", 我来了: " + i);
            if (i == 3) {
                // 控制结束的语句不是  Flux.create 生产方控制
                outSink.complete();
            }
        }
    }

    public static void init() {
        Flux<String> f = Flux.create(new Consumer<FluxSink<String>>() {
            @Override
            public void accept(FluxSink<String> sink) {
                outSink = sink;
            }
        });
        f.subscribe(System.out::println);
    }


}
