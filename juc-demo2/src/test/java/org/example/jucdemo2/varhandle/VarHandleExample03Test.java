package org.example.jucdemo2.varhandle;

import lombok.ToString;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;

public class VarHandleExample03Test {



    /**
     * 普通类的成员变量，构造方法和成员方法的访问
     */
    @Nested
    class VarHandleAccessModeTest {

        @ToString
        static class Person {

            public Person() {
                System.out.println("Person constructor");
            }

            private int a = 3;
            int b = 4;
            protected int c = 5;
            public int d = 6;
            public static int e = 6;
            private final int[] arrayData = {1, 2, 3};
        }

        @Test
        void testPrivateMemberAccess() throws IllegalAccessException, NoSuchFieldException {
            Person instance = new Person();
            // private
            // 这里不需要设置access访问权限
            VarHandle varHandle = MethodHandles.privateLookupIn(Person.class, MethodHandles.lookup())
                    // 用于创建对象中非静态字段的VarHandle。接收参数有三个，第一个为接收者的class对象，第二个是字段名称，第三个是字段类型
                    .findVarHandle(Person.class, "a", int.class);
            varHandle.set(instance, 33);
            System.out.println(instance);

        }

        @Test
        void testDefaultMemberAccess() throws IllegalAccessException, NoSuchFieldException {
            Person instance = new Person();
            // protected
            VarHandle varHandle = MethodHandles.lookup()
                    .in(Person.class)
                    .findVarHandle(Person.class, "b", int.class);
            varHandle.set(instance, 33);
            System.out.println(instance);
        }

        @Test
        void testProtectedMemberAccess() throws IllegalAccessException, NoSuchFieldException {
            Person instance = new Person();
            // protected
            VarHandle varHandle = MethodHandles.lookup()
                    .in(Person.class)
                    .findVarHandle(Person.class, "c", int.class);
            varHandle.set(instance, 33);
            System.out.println(instance);
        }

        @Test
        public void testPublicMemberAccess() throws IllegalAccessException, NoSuchFieldException {
            Person instance = new Person();
            // public
            // MethodHandles.lookup() 获取访问protected、public的Lookup
            VarHandle varHandle = MethodHandles.lookup()
                    .in(Person.class)
                    .findVarHandle(Person.class, "d", int.class);
            varHandle.set(instance, 33);
            System.out.println(instance);

            // 只能处理public类的public成员
            MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        }

        @Test
        public void testStaticMemberAccess() throws IllegalAccessException, NoSuchFieldException {
            // static
            // MethodHandles.lookup() 获取访问protected、public的Lookup
            VarHandle varHandle = MethodHandles.lookup()
                    .in(Person.class)
                    .findStaticVarHandle(Person.class, "e", int.class);
            varHandle.set(456);
            System.out.println(Person.e);
        }

        @Test
        void testArrayMemberAccess() throws IllegalAccessException, NoSuchFieldException {
            Person instance = new Person();
            //  获取管理数组的 Varhandle
            VarHandle arrayVarHandle = MethodHandles.arrayElementVarHandle(int[].class);
            arrayVarHandle.compareAndSet(instance.arrayData, 0, 1, 11);
            arrayVarHandle.compareAndSet(instance.arrayData, 1, 2, 22);
            boolean compareAndSet = arrayVarHandle.compareAndSet(instance.arrayData, 2, 355, 33);
            System.out.println(compareAndSet); // false
            System.out.println(instance);
        }

        /**
         * unreflectVarHandle：通过反射字段Field创建VarHandle
         */
        @Test
        void testReflect() throws IllegalAccessException, NoSuchFieldException {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            Field a = Person.class.getDeclaredField("a");
            System.out.println(a);
            VarHandle varHandle = lookup.unreflectVarHandle(a);
            Person p = new Person();
            varHandle.set(p, 123); // 修改

            System.out.println(p.a);

            Object o = varHandle.get(p); // 获取
            System.out.println(o);

        }

    }
}


/*
VarHandle来使用plain、opaque、release/acquire和volatile四种共享内存的访问模式，根据这四种共享内存的访问模式又分为写入访问模式、读取访问模式、原子更新访问模式、数值更新访问模式、按位原子更新访问模式。

写入访问模式(write access modes)
获取指定内存排序效果下的变量值，包含的方法有get、getVolatile、getAcquire、getOpaque 。

读取访问模式(read access modes)
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

 */