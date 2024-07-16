package com.zishi.react;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/**
 * 异步单线程创建 Flux push 方法: 同create
 */
public class FluxDemo06Test {

    @Test
    void testFluxPush01() {

        Flux.push(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::println);
    }

}
























