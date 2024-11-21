package org.example.jucdemo2.atomic;

import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder
 */
public class LongAdderDemoTest {

    public static void main(String[] args) throws InterruptedException {

    }

    @RepeatedTest(value = 10)
    void test() throws InterruptedException {


        A a = new A();
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    a.adder.add(1);
                    a.num += 1;
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < 1000; i++) {
            threads[i].join();
        }
        System.out.println("adder:" + a.adder.sum());
        System.out.println("num:" + a.num);
    }
}

class A {
    public A() {
        System.out.println("222222222222");
    }
    LongAdder adder = new LongAdder();
    public int num = 0;
}