package com.zishi.creational.sington.im04;

/**
 * 静态内部类方式
 * 代码可以看出，静态内部类和饿汉方式有异曲同工之妙，
 * 它们都采用了类装载的机制来保证，
 * 当初始化实例时只有一个线程执行，从而保证了多线程下的安全操作。
 * JVM 会在类初始化阶段（也就是类装载阶段）创建一个锁，
 * 该锁可以保证多个线程同步执行类初始化的工作，因此在多线程环境下，类加载机制依然是线程安全的。
 *
 *
 * 但静态内部类和饿汉方式也有着细微的差别，
 * 饿汉方式是在程序启动时就会进行加载，因此可能造成资源的浪费；
 * 而静态内部类只有在调用 getInstance() 方法时，才会装载内部类从而完成实例的初始化工作，因此不会造成资源浪费的问题。
 * 由此可知，此方式也是较为推荐的单例实现方式。
 *
 *
 */
public class Singleton {
    private Singleton() {
    }

    // 获取实例（单例对象）
    public static Singleton getInstance() {
        return SingletonInstance.instance;
    }

    // 类方法
    public void sayHi() {
        System.out.println("Hi,Java.");
    }

    // 静态内部类
    private static class SingletonInstance {
        private static final Singleton instance = new Singleton();
    }
}
