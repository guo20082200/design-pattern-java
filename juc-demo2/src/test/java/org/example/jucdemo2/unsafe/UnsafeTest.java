package org.example.jucdemo2.unsafe;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.io.File;
import java.io.FileInputStream;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * 参考：https://javabetter.cn/thread/Unsafe.html
 */
public class UnsafeTest {

    /**
     * 获取 Unsafe 实例
     *
     * @throws IllegalAccessException
     */
    @Test
    void testConstructor() throws IllegalAccessException {

        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Object object = unsafeField.get(null);
        System.out.println(object.getClass()); // class sun.misc.Unsafe

        if (object instanceof Unsafe unsafe) {

            System.out.println(unsafe.getClass().getName());
        }

        // 获取 Unsafe 实例
        //Unsafe unsafe2 = Unsafe.getUnsafe();

    }

    static Unsafe unsafe;

    static {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        try {
            unsafe = (Unsafe) unsafeField.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 1. Unsafe的内存管理功能主要包括：普通读写、volatile读写、有序写入、直接操作内存等分配内存与释放内存的功能。
     */
    @Test
    void test01() throws NoSuchFieldException, InstantiationException {

        //1. 普通读写
        //Unsafe可以读写一个类的属性，即便这个属性是私有的，也可以对这个属性进行读写。
        User user = new User("zhangsan", 23);
        long fieldOffset = unsafe.objectFieldOffset(User.class.getDeclaredField("age"));
        System.out.println("offset:" + fieldOffset);
        //public native void putInt(Object o, long offset, int x);
        // putInt用于在对象指定偏移地址处写入一个int，并且即使类中的这个属性是private类型的，也可以对它进行读写。
        unsafe.putInt(user, fieldOffset, 20);

        // public native int getInt(Object o, long offset);
        // getInt用于从对象的指定偏移地址处读取一个int，
        System.out.println("age:" + unsafe.getInt(user, fieldOffset));
        System.out.println("age:" + user.getAge());

        //2. 创建对象实例，无论对象的构造方法是不是private
        User user3 = (User) unsafe.allocateInstance(User.class);
        System.out.println(user3.getAge());

    }

    /**
     * 2. 内存操作
     * 分配新的本地空间
     * public native long allocateMemory(long bytes);
     * //重新调整内存空间的大小
     * public native long reallocateMemory(long address, long bytes);
     * //将内存设置为指定值
     * public native void setMemory(Object o, long offset, long bytes, byte value);
     * //内存拷贝
     * public native void copyMemory(Object srcBase, long srcOffset,Object destBase, long destOffset,long bytes);
     * //清除内存
     * public native void freeMemory(long address);
     */
    @Test
    void memoryTest() {
        int size = 4;
        //分配一块新的native内存空间，大小是size，单位是bytes
        // 通过这种方式分配的内存属于堆外内存，是无法进行垃圾回收的，
        // 需要我们把这些内存当做一种资源去手动调用freeMemory方法进行释放，否则会产生内存泄漏。
        long addr = unsafe.allocateMemory(size);
        // 重新调正内存空间的大小
        long addr3 = unsafe.reallocateMemory(addr, size * 2);
        System.out.println("addr: " + addr);
        System.out.println("addr3: " + addr3);
        try {
            unsafe.setMemory(null, addr, size, (byte) 1);
            for (int i = 0; i < 2; i++) {
                unsafe.copyMemory(null, addr, null, addr3 + size * i, 4);
            }
            System.out.println(unsafe.getInt(addr));
            System.out.println(unsafe.getLong(addr3));
        } finally {
            // 释放内存
            unsafe.freeMemory(addr);
            unsafe.freeMemory(addr3);
        }
    }

    /**
     * 3.
     * //禁止读操作重排序
     * public  native  void  loadFence();
     * //禁止写操作重排序
     * public  native  void  storeFence();
     * //禁止读、写操作重排序
     * public  native  void  fullFence();
     */
    public static void main(String[] args) {
        ChangeThread changeThread = new ChangeThread();
        new Thread(changeThread).start();
        while (true) {
            boolean flag = changeThread.isFlag();
            System.out.println(flag);
            // 不加入内存屏障，该while循环不会停止
            unsafe.loadFence(); //加入读内存屏障
            if (flag) {
                System.out.println("detected flag changed");
                break;
            }
        }
        System.out.println("main thread end");
    }

    /**
     * 4. 数组的操作
     */
    @Test
    void arrayTest() {

        //String 数组对象的内存布局
        // String 数组对象中，对象头包含 3 部分，mark word标记字占用 8 字节，klass point类型指针占用 4 字节，数组对象特有的数组长度部分占用 4 字节，总共占用了 16 字节。
        String[] array = new String[]{"str1str1str", "str2", "str3"};

        /**
         * 返回给定的array在分配内存的第一个元素的偏移量
         * 如果针对同一个数组， arrayIndexScale 方法返回了一个non-zero值，
         * 那么你可以使用scale因子和base偏移量，可以形成一个新的偏移量来获取数组中的元素
         */
        int baseOffset = unsafe.arrayBaseOffset(String[].class);
        System.out.println(baseOffset);
        /**
         * 返回数组中元素占内容空间的大小
         */
        int scale = unsafe.arrayIndexScale(String[].class);
        System.out.println(scale);

        for (int i = 0; i < array.length; i++) {
            int offset = baseOffset + scale * i;
            System.out.println(offset + "  :  " + unsafe.getObject(array, offset));
        }

        // 一个致命的错误
        //System.out.println(17 + "  :  " + unsafe.getObject(array, 17));
    }


    /**
     * CAS 操作
     */
    static class CasTest {
        private volatile int a;

        public static void main(String[] args) {
            CasTest casTest = new CasTest();
            new Thread(() -> {
                for (int i = 1; i < 5; i++) {
                    casTest.increment(i);
                    System.out.println(Thread.currentThread().getName() + "..." + casTest.a + " ");
                }
            }, "a").start();
            new Thread(() -> {
                for (int i = 5; i < 10; i++) {
                    casTest.increment(i);
                    System.out.println(Thread.currentThread().getName() + "..." + casTest.a + " ");
                }
            }, "b").start();
        }

        /**
         * 在调用compareAndSwapInt方法后，会直接返回true或false的修改结果，因此需要我们在代码中手动添加自旋的逻辑。
         * @param x
         */
        private void increment(int x) {
            while (true) {
                try {
                    long fieldOffset = unsafe.objectFieldOffset(CasTest.class.getDeclaredField("a"));
                    if (unsafe.compareAndSwapInt(this, fieldOffset, x - 1, x)) {
                        break;
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 6. 线程调度
     * LockSupport 底层的实现就是 Unsafe
     *
     * 参考：https://javabetter.cn/thread/LockSupport.html
     */
    @Test
    void threadTest() {
        Thread mainThread = Thread.currentThread();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("subThread try to unpark mainThread");
                unsafe.unpark(mainThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("park main mainThread");
        unsafe.park(false,0L);
        System.out.println("unpark mainThread success");
    }

    @Test
    void classTest() throws Exception {
        User user=new User("AAA", 23);

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Class<?> aClass = lookup.ensureInitialized(User.class);
        System.out.println(aClass); // class org.example.jucdemo2.unsafe.User
        //System.out.println(unsafe.shouldBeInitialized(User.class));
        Field sexField = User.class.getDeclaredField("ddd");
        long fieldOffset = unsafe.staticFieldOffset(sexField);
        Object fieldBase = unsafe.staticFieldBase(sexField);
        Object object = unsafe.getObject(fieldBase, fieldOffset);
        System.out.println(object);

    }


    /**
     * 8. 系统信息
     * Unsafe 中提供的addressSize和pageSize方法用于获取系统信息，
     * 调用addressSize方法会返回系统指针的大小，如果在 64 位系统下默认会返回 8，而 32 位系统则会返回 4。
     * 调用 pageSize 方法会返回内存页的大小，值为 2 的整数幂。
     */
    @Test
    void systemInfoTest() throws Exception {
        System.out.println(unsafe.addressSize());
        System.out.println(unsafe.pageSize());
    }


}

@Getter
class ChangeThread implements Runnable {
    /**
     * volatile
     **/
    boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("subThread  change  flag  to:" + flag);
        flag = true;
    }
}














