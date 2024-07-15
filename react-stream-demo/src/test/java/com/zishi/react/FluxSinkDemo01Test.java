package com.zishi.react;

import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.atomic.LongAdder;



public class FluxSinkDemo01Test {

    @Test
    void testHandlingErrors01() throws IOException {
        Flux<String> flux = Flux.just(1, 2, 0, 3, 4, 5)
                .map(i -> "100 / " + i + " = " + (100 / i)) //this triggers an error with 0
                .onErrorReturn("Divided by zero :(");// error handling example


        flux.subscribe(System.out::println);
    }

}
























