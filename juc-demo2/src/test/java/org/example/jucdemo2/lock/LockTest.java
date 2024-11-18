package org.example.jucdemo2.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class LockTest {

    @Test
    void lock01() {

        // 悲观锁
        synchronized (LockTest.class) {

        }
    }

    @Test
    void lock02() {

        // 乐观锁
        // 通过版本控制
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
    }
}
