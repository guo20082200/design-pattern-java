package org.example.jucdemo2.varhandle;

import lombok.ToString;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.lang.invoke.WrongMethodTypeException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class VarHandleExample04Test {


    /**
     * 普通类的成员变量，构造方法和成员方法的访问
     */
    @Nested
    class VarHandleAccessModeTest {

        @ToString
        static class Person {
            private int a = 3;
            int b = 4;
            protected int c = 5;
            public int d = 6;
            public static int e = 6;
            private final int[] arrayData = {1, 2, 3};

            private final int f = 3;
            private volatile int g = 3;
        }

        @Test
        void testFinalAndVolatile() throws IllegalAccessException, NoSuchFieldException {
            Person instance = new Person();
            VarHandle varHandle = MethodHandles.privateLookupIn(Person.class, MethodHandles.lookup())
                    .findVarHandle(Person.class, "f", int.class);
            //varHandle.set(instance, 33); // 不能修改final UnsupportedOperationException

            VarHandle varHandle2 = MethodHandles.privateLookupIn(Person.class, MethodHandles.lookup())
                    .findVarHandle(Person.class, "g", int.class);
            varHandle2.set(instance, 33);
            System.out.println(varHandle2.get(instance)); //33

            //ConstantBootstraps.staticFieldVarHandle()
        }


        /**
         * set 和 setOpaque 区别：
         *
         * @throws IllegalAccessException
         * @throws NoSuchFieldException
         */
        @Test
        void testPrivateMemberAccess() throws IllegalAccessException, NoSuchFieldException {
            Person instance = new Person();
            // private
            // 这里不需要设置access访问权限
            VarHandle varHandle = MethodHandles.privateLookupIn(Person.class, MethodHandles.lookup())
                    // 用于创建对象中非静态字段的VarHandle。接收参数有三个，第一个为接收者的class对象，第二个是字段名称，第三个是字段类型
                    .findVarHandle(Person.class, "a", int.class);

            /**
             * 1. 设置当前变量的值为一个新的值（按照代码的顺序），变量不能被final和volatile修饰，参考普通的写操作
             * 2. 该方法的签名为：(CT1 ct1, ..., CTn ctn, T newValue)void
             * 3. 调用set方法传入的参数类型必须和access mode type保持一致
             *
             * @param args 参数列表 (CT1 ct1, ..., CTn ctn, T newValue)，静态使用varargs
             * @throws UnsupportedOperationException 针对当前的VarHandle 如果 the access mode 不支持
             * @throws WrongMethodTypeException 如果access mode type 不匹配调用者传入参数的类型
             * @throws ClassCastException 如果the access mode type匹配调用者传入参数的类型，但是引用转化失败
             */
            varHandle.set(instance, 33);

            /**
             *
             * 1. 设置当前变量的值为一个新的值（按照代码的顺序），但是相对于其他线程，不能保证内存一致性
             * 2. 该方法的签名为：(CT1 ct1, ..., CTn ctn, T newValue)void
             * 3. 调用setOpaque方法传入的参数类型必须和access mode type保持一致
             *
             * @param args 参数列表 (CT1 ct1, ..., CTn ctn, T newValue)，静态使用varargs
             * @throws UnsupportedOperationException  针对当前的VarHandle 如果 the access mode 不支持
             * @throws WrongMethodTypeException 如果access mode type 不匹配调用者传入参数的类型
             * @throws ClassCastException 如果the access mode type匹配调用者传入参数的类型，但是引用转化失败
             */
            varHandle.setOpaque(instance, 33);
            // access mode type
            MethodType methodType = varHandle.accessModeType(VarHandle.AccessMode.SET_OPAQUE);
            System.out.println(methodType); // (Person,int)void
            System.out.println(varHandle.getOpaque(instance));
            Class<?> aClass = varHandle.varType();
            Optional<VarHandle.VarHandleDesc> varHandleDesc1 = varHandle.describeConstable();
            System.out.println(varHandleDesc1.get()); // VarHandleDesc[VarHandleExample04Test$VarHandleAccessModeTest$Person.a:int]



            /*VarHandle.VarHandleDesc varHandleDesc =
                    VarHandle.VarHandleDesc.ofField(ClassDesc.of(Person.class.getName()), "a", ClassDesc.of(int.class.getName()));
            System.out.println(varHandleDesc.varType());*/

        }

        /**
         * set 和 setOpaque 区别：
         * 1. 方法声明
         * void set(Object... vars)
         * void setOpaque(Object... vars)
         * 2. 可见性
         *
         * 在Java的VarHandle API中，set和setOpaque方法都用于设置对象字段的值，但它们在内存模型和可见性方面有不同的语义。以下是它们之间的主要区别：1. set
         * •方法签名：void set(Object... vars)
         * •语义：set方法用于将指定的值设置到目标字段。它遵循Java内存模型的标准规则，即在设置值之前对其他线程所做的更改是可见的。
         * •可见性：在调用set方法之后，其他线程可能会看到该字段的旧值或新值，具体取决于线程调度和内存屏障的实现。
         * 2. setOpaque
         * •方法签名：void setOpaque(Object... vars)
         * •语义：setOpaque方法用于将指定的值设置到目标字段，但它提供了一种“不透明”的写操作。这意味着在setOpaque调用之前对其他变量的修改对后续的读操作是不可见的。
         * •可见性：在调用setOpaque方法之后，其他线程不会看到该字段的旧值，但对setOpaque调用之前的任何其他变量的修改也不会对后续的读操作可见。
         * 换句话说，setOpaque只保证了当前写操作的可见性，而忽略了之前的写操作。
         *
         */
        @Test
        void testSetOpaqueAndSet() {
            /*try {
                VarHandle vh = MethodHandles.lookup().findVarHandle(MyClass.class, "value", int.class);
                MyClass obj = new MyClass();

                // 使用 set 方法： 标准的写操作，遵循Java内存模型的可见性规则。

                vh.set(obj, 10);
                System.out.println("After set: " + obj.value); // 输出: After set: 10

                // 使用 setOpaque 方法：setOpaque：不透明的写操作，只保证当前写操作的可见性，忽略之前的写操作。
                vh.setOpaque(obj, 20);
                System.out.println("After setOpaque: " + obj.value); // 输出: After setOpaque: 20
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }*/
        }



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