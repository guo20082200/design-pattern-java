package com.zishi.pattern.creational.sington.im03;

/**
 * 双重检测锁
 * 为了保证懒汉模式的线程安全我们最简单的做法就是给获取实例的方法上加上 synchronized（同步锁）修饰
 */
public class Singleton {
    // 声明私有对象
    private volatile static Singleton instance;

    private Singleton() {
    }

    // 获取实例（单例对象）
    // 这样虽然能让懒汉模式变成线程安全的，但由于整个方法都被 synchronized 所包围，因此增加了同步开销，降低了程序的执行效率
    public synchronized static Singleton getInstance01() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // 获取实例（单例对象）
    // 细心的你可能会发现以下的代码也存在着非线程安全的问题。
    // 例如，当两个线程同时执行到if (instance == null) {判断时，判断的结果都为 true，于是他们就排队都创建了新的对象，这显然不符合我们的预期
    public static Singleton getInstance02() {
        if (instance == null) {
            synchronized (Singleton.class) {
                instance = new Singleton();
            }
        }
        return instance;
    }

    /**
     * Double Checked Lock，DCL
     *
     * 代码看似完美，其实隐藏着一个不容易被人发现的小问题，
     * 该问题就出在 new 对象这行代码上，
     * 也就是 instance = new Singleton() 这行代码。
     * 这行代码看似是一个原子操作，然而并不是，这行代码最终会被编译成多条汇编指令，它大致的执行流程为以下三个步骤：
     *
     * 1. 给对象实例分配内存空间；
     * 2. 调用对象的构造方法、初始化成员字段；
     * 3. 将 instance 对象指向分配的内存空间。
     *
     * 但由于 CPU 的优化会对执行指令进行重排序，也就说上面的执行流程的执行顺序有可能是 1-2-3，也有可能是 1-3-2。
     * 假如执行的顺序是 1-3-2，那么当 A 线程执行到步骤 3 时，切换至 B 线程了，而此时 B 线程判断 instance 对象已经指向了对应的内存空间，并非为 null 时就会直接进行返回，而此时因为没有执行步骤 2，因此得到的是一个未初始化完成的对象，这样就导致了问题的诞生。执行时间节点如下表所示：
     *
     * 时间点	线程	    执行操作
     * t1	    A	    instance = new Singleton() 的 1-3 步骤，待执行步骤 2
     * t2	    B	    if (instance == null) { 判断结果为 false
     * t3	    B	    返回半初始的 instance 对象
     *
     *
     * 为了解决此问题，我们可以使用关键字 volatile 来修饰 instance 对象，这样就可以防止 CPU 指令重排，从而完美地运行懒汉模式
     */
    public static Singleton getInstance03() {
        // 第一次判断
        if (instance == null) {
            synchronized (Singleton.class) {
                // 第二次判断
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static Singleton getInstance() {
        // 第一次判断
        if (instance == null) {
            synchronized (Singleton.class) {
                // 第二次判断
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    // 类方法
    public void sayHi() {
        System.out.println("Hi,Java.");
    }
}
