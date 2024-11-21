package org.example.jucdemo2.atomic;

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
        LongAccumulator accumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        }, 3);

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
}
