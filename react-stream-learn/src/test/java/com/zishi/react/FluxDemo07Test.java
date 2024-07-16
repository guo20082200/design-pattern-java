package com.zishi.react;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

/**
 * Flux handle
 */
public class FluxDemo07Test {


    /**
     *
     * Flux handle: 有一点不同，handle是一个实例方法，也就是说作用在一个已经存在的source上。Mono也有同样的方法
     * 类似：generate，使用SynchronousSink，仅仅允许一对一的发射信号。
     * 然而handle可以用来发出任意的值，甚至跳过一些元素。可以当成map和filter的结合体
     *
     */
    @Test
    void testFluxPush01() {

        Flux<String> alphabet = Flux.just(-1, 30, 13, 9, 20)
                .handle((i, sink) -> {
                    String letter = alphabet(i);
                    if (letter != null)
                        sink.next(letter);
                });

        alphabet.subscribe(System.out::println);
    }

    public String alphabet(int letterNumber) {
        if (letterNumber < 1 || letterNumber > 26) {
            return null;
        }
        int letterIndexAscii = 'A' + letterNumber - 1;
        return "" + (char) letterIndexAscii;
    }

}
























