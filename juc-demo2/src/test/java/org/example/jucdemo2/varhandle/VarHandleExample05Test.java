package org.example.jucdemo2.varhandle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.TimeUnit;

public class VarHandleExample05Test {
    static boolean flag = true;

    static final VarHandle vh;
    static final VarHandle vh2;

    static {
        try {
            vh = MethodHandles.lookup().findVarHandle(MyClass.class, "value", int.class);
            vh2 = MethodHandles.lookup().findStaticVarHandle(MyClass.class, "value2", int.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws InterruptedException {

        MyClass obj = new MyClass();

        new Thread(() -> {
            while (true) {
                if (100 == Integer.parseInt(vh2.get().toString())) {
                    System.out.println(".............");
                    break;
                }
            }
        }, "b").start();

        TimeUnit.SECONDS.sleep(2);
        vh2.set(100);
        System.out.println("obj 的value值为: " + vh2.get());
    }

    static class MyClass {
        int value;
        static int value2;
    }
}


/*

访问模式控制着原子性和一致性属性，对于大多数方法来说，有以下几种内存排序效果：

对于引用类型和32位以内的原始类型，read和write(get、set)都可以保证原子性，并且对于执行线程以外的线程不施加可观察的排序约束。
不透明属性：访问相同变量时，不透明操作按原子顺序排列。
Acquire模式的读取总是在与它对应的Release模式的写入之后。
所有Volatile变量的操作相互之间都是有序的。

VarHandle来使用plain、opaque、release/acquire和volatile四种共享内存的访问模式，
根据这四种共享内存的访问模式又分为写入访问模式、读取访问模式、原子更新访问模式、数值更新访问模式、按位原子更新访问模式。

读取访问模式(write access modes)
获取指定内存排序效果下的变量值，包含的方法有get、getVolatile、getAcquire、getOpaque 。

写入访问模式(read access modes)
在指定的内存排序效果下设置变量的值，包含的方法有set、setVolatile、setRelease、setOpaque 。

原子更新模式(atomic update access modes)
原子更新访问模式，例如，在指定的内存排序效果下，原子的比较和设置变量的值，
包含的方法有compareAndSet、weakCompareAndSetPlain、weakCompareAndSet、weakCompareAndSetAcquire、
weakCompareAndSetRelease、compareAndExchangeAcquire、compareAndExchange、compareAndExchangeRelease、
getAndSet、getAndSetAcquire、getAndSetRelease 。

数值更新访问模式(numeric atomic update access modes)
数字原子更新访问模式，例如，通过在指定的内存排序效果下添加变量的值，以原子方式获取和设置。
包含的方法有getAndAdd、getAndAddAcquire、getAndAddRelease 。

按位原子更新访问模式(bitwise atomic update access modes )
按位原子更新访问模式，例如，在指定的内存排序效果下，以原子方式获取和按位OR变量的值。
包含的方法有getAndBitwiseOr、getAndBitwiseOrAcquire、getAndBitwiseOrRelease、
getAndBitwiseAnd、getAndBitwiseAndAcquire、getAndBitwiseAndRelease、
getAndBitwiseXor、getAndBitwiseXorAcquire ， getAndBitwiseXorRelease 。


VarHandle 除了支持各种访问模式下访问变量之外，还提供了一套内存屏障方法，目的是为了给内存排序提供更细粒度的控制。主要如下几个方法：

public static void fullFence() {
    UNSAFE.fullFence();
}
public static void acquireFence() {
    UNSAFE.loadFence();
}
public static void releaseFence() {
    UNSAFE.storeFence();
}
public static void loadLoadFence() {
    UNSAFE.loadLoadFence();
}
public static void storeStoreFence() {
    UNSAFE.storeStoreFence();
}
 */