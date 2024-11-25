package org.example.jucdemo2.varhandle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.List;

public class VarHandleExample02Test {

    @Nested
    class VarHandleAccessModeTest {
        /**
         * AccessMode 说明一个VarHandle的变量或者引用如何被访问
         */
        @Test
        void testVarHandleAccessMode() {

            VarHandle.AccessMode[] accessModeArr = VarHandle.AccessMode.values();
            for (VarHandle.AccessMode mode : accessModeArr) {
                System.out.println(mode.methodName());
            }

            System.out.println(accessModeArr[0]);

            // access mode 对应的access 是对应的方法的名字
            VarHandle.AccessMode accessMode = VarHandle.AccessMode.valueFromMethodName("get");
            System.out.println(accessMode.methodName());

        }
    }

    @Nested
    @DisplayName("测试VarHandle构造方法来访问数组的操作")
    class VarHandleTest {

        @Test
        @DisplayName("测试VarHandle访问String[]")
        void testVarHandle01() {
            String[] arr = {"1", "2", "wwww"};
            VarHandle avh = MethodHandles.arrayElementVarHandle(String[].class);
            System.out.println(avh.getClass()); // class java.lang.invoke.VarHandleReferences$Array
            List<Class<?>> classes = avh.coordinateTypes();
            System.out.println(classes.size()); // 2
            System.out.println(classes.get(0)); // class [Ljava.lang.String;
            System.out.println(classes.get(1)); // int

            // 返回被当前VarHandle引用的变量(variables)的类型
            System.out.println(avh.varType()); // class java.lang.String

            // 返回一个当前实例的nominal descriptor,如果有的话就构造一个, 如果没有就返回一个空Optional
            System.out.println(avh.describeConstable()); //Optional[VarHandleDesc[String[][]]]

            // 返回变量的值，其内存语义类似于该变量被声明为非volatile时的读取方式。通常称为普通的读取访问。
            // 方法的签名格式如: (CT1 ct1, ..., CTn ctn)T
            // 在 get 方法的调用点，符号类型描述符必须与通过调用 accessModeType(VarHandle.AccessMode.GET) 方法得到的访问模式类型相匹配。
            // 参数解释:
            // args: 方法参数 (CT1 ct1, ..., CTn)

            // class java.lang.invoke.VarHandleReferences$Array 这个类里面get方法定义如下:
            // static Object get(VarHandle ob, Object oarray, int index)
            System.out.println(avh.get(arr, 2));

            // class java.lang.invoke.VarHandleReferences$Array 这个类里面set方法定义如下:
            // static void set(VarHandle ob, Object oarray, int index, Object value)
            avh.set(arr, 2, "4000444");
            System.out.println(avh.get(arr, 2));
        }

        @Test
        @DisplayName("测试VarHandle访问int[]")
        void testVarHandle02() {
            int[] arr = {1, 2, 3};
            VarHandle avh = MethodHandles.arrayElementVarHandle(int[].class);
            System.out.println(avh.getClass()); // class java.lang.invoke.VarHandleInts$Array
            List<Class<?>> classes = avh.coordinateTypes();
            System.out.println(classes.size()); // 2
            System.out.println(classes.get(0)); // class [I
            System.out.println(classes.get(1)); // int
            System.out.println(avh.varType()); // int
            System.out.println(avh.describeConstable()); //Optional[VarHandleDesc[int[][]]]
            System.out.println(avh.get(arr, 2)); // 3
        }

        @Test
        @DisplayName("测试VarHandle访问Integer[]")
        void testVarHandle03() {
            Integer[] arr = {1, 2, 3};
            VarHandle avh = MethodHandles.arrayElementVarHandle(Integer[].class);
            System.out.println(avh.getClass()); // class java.lang.invoke.VarHandleReferences$Array
            List<Class<?>> classes = avh.coordinateTypes();
            System.out.println(classes.size()); // 2
            System.out.println(classes.get(0)); // class [Ljava.lang.Integer;
            System.out.println(classes.get(1)); // int
            System.out.println(avh.varType()); // class java.lang.Integer
            System.out.println(avh.describeConstable()); // Optional[VarHandleDesc[Integer[][]]]
            System.out.println(avh.get(arr, 2)); // 3
        }


        @Test
        @DisplayName("测试VarHandle访问Person[]")
        void testVarHandle04() {
            Person[] arr = {new Person("ZA"), new Person("WW"), new Person("FF")};
            VarHandle avh = MethodHandles.arrayElementVarHandle(Person[].class);
            System.out.println(avh.getClass()); // class java.lang.invoke.VarHandleReferences$Array
            List<Class<?>> classes = avh.coordinateTypes();
            System.out.println(classes.size()); // 2
            System.out.println(classes.get(0)); // class [Lorg.example.jucdemo2.varhandle.VarHandleExample02Test$VarHandleTest$Person;
            System.out.println(classes.get(1)); // int
            System.out.println(avh.varType()); // class org.example.jucdemo2.varhandle.VarHandleExample02Test$VarHandleTest$Person
            System.out.println(avh.describeConstable()); // Optional[VarHandleDesc[VarHandleExample02Test$VarHandleTest$Person[][]]]
            System.out.println(avh.get(arr, 2)); // VarHandleExample02Test.VarHandleTest.Person(name=FF)
        }

        @Data
        @AllArgsConstructor
        @ToString
        private static class Person {
            public String name;
        }

    }

    @Nested
    @DisplayName("测试VarHandle 访问一个类的成员方法")
    class VarHandle02Test {

        @Data
        @AllArgsConstructor
        @ToString
        private static class Student {
            public String name;
        }

        @Test
        @DisplayName("VarHandle 访问类的普通成员变量")
        void testVarHandle01() throws NoSuchFieldException, IllegalAccessException {
            Student student = new Student("zhangsan");

            MethodHandles.Lookup l = MethodHandles.lookup();
            VarHandle varHandle = l.findVarHandle(Student.class, "name", String.class);
            System.out.println(varHandle); // VarHandle[varType=java.lang.String, coord=[class org.example.jucdemo2.varhandle.VarHandleExample02Test$VarHandle02Test$Student]]

            System.out.println(varHandle.get(student));// 获取student这个对象的属性值
            varHandle.set(student, "erfearfaer");
            System.out.println(varHandle.get(student)); //erfearfaer

        }

    }

    @Nested
    @DisplayName("测试VarHandle构造方法来访问数组的操作")
    class VarHandleArrayConstructorTest {

        @Test
        @DisplayName("ArrayConstructor 基本类型")
        void testArrayConstructor01() {
            /*
             * MethodHandle(int)boolean[]
             * MethodHandle(int)short[]
             * MethodHandle(int)char[]
             * MethodHandle(int)int[]
             * MethodHandle(int)long[]
             * MethodHandle(int)float[]
             * MethodHandle(int)double[]
             */
            Class<?>[] classes = {boolean[].class, short[].class, char[].class, int[].class, long[].class, float[].class, double[].class};
            for (Class<?> aClass : classes) {
                MethodHandle methodHandle = MethodHandles.arrayConstructor(aClass);
                System.out.println(methodHandle);
                //System.out.println(methodHandle.getClass()); // class java.lang.invoke.BoundMethodHandle$Species_LLL
            }

            /**
             * MethodHandle(int)Boolean[]
             * MethodHandle(int)Byte[]
             * MethodHandle(int)Short[]
             * MethodHandle(int)Character[]
             * MethodHandle(int)Integer[]
             * MethodHandle(int)Long[]
             * MethodHandle(int)Float[]
             * MethodHandle(int)Double[]
             */
            Class<?>[] classes2 = {Boolean[].class, Byte[].class, Short[].class, Character[].class, Integer[].class, Long[].class, Float[].class, Double[].class};
            for (Class<?> aClass : classes2) {
                MethodHandle methodHandle = MethodHandles.arrayConstructor(aClass);
                System.out.println(methodHandle);
                //System.out.println(methodHandle.getClass()); // class java.lang.invoke.BoundMethodHandle$Species_LLL
            }

            System.out.println("...........................");

            MethodHandle methodHandle2 = MethodHandles.arrayConstructor(String[].class);
            System.out.println(methodHandle2.type()); // (int)String[]

            System.out.println("...........................");

            MethodHandle methodHandle3 = MethodHandles.arrayConstructor(VarHandleTest.Person[].class);
            System.out.println(methodHandle3.type()); // (int)Person[]

        }

    }


}
