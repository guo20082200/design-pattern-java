package org.example.jucdemo2.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;

public class LongAccumulatorDemoTest {

    public static void main(String[] args) {

        /*
         * 通过accumulator function 和 identity element 创建一个新的实例
         * accumulator function：side-effect-free 函数
         * identity identity：accumulator function的初始值
         *
         */
        LongAccumulator accumulator = new LongAccumulator(Long::sum, 0);

        /*int intValue = accumulator.intValue();
        System.out.println(intValue);

        System.out.println(accumulator.floatValue());
        System.out.println(accumulator.longValue());
        System.out.println(accumulator.doubleValue());
        System.out.println(accumulator.toString());*/

        accumulator.accumulate(2L);
        accumulator.accumulate(2L);
        System.out.println(accumulator.intValue());
        System.out.println(accumulator.get());

        /*
        * 重置，结果回到构造方法的初始值
         */
       // accumulator.reset();
        //System.out.println(accumulator.intValue());

        System.out.println(accumulator.getThenReset());

    }


    /**
     * 多线程使用累加器: LongAccumulator
     * 线程安全的累加器
     */
    @Test
    void test() {
        // 创建线程池
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        final LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 5);

        for(int i = 1; i <= 100; i++) {
            final int index = i * 100;
            executorService.submit(() -> longAccumulator.accumulate(index));
        }

        executorService.shutdown();
        while(!executorService.isTerminated()) {

        }
        System.out.println("value=" + longAccumulator.get()); //value=5505
    }

}
