package com.zishi.pattern.creational.sington.im02;

/**
 * 懒汉模式:
 *
 * 它的优点是不会造成资源的浪费，因为在调用的时候才会创建被实例化对象；
 * 它的缺点在多线程环境下是非线程是安全的，比如多个线程同时执行到 if 判断处，此时判断结果都是未被初始化，那么这些线程就会同时创建 n 个实例，这样就会导致意外的情况发生。
 *
 * 使用单例模式可以减少系统的内存开销，提高程序的运行效率，
 * 但是使用不当的话就会造成多线程下的并发问题。
 * 饿汉模式为最直接的实现单例模式的方法，但它可能会造成对系统资源的浪费，所以只有既能保证线程安全，
 * 又可以避免系统资源被浪费。
 */
public class Singleton {
    // 声明私有对象
    private static Singleton instance;

    // 获取实例（单例对象）
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    private Singleton() {
    }

    // 方法
    public void sayHi() {
        System.out.println("Hi,Java.");
    }
}

class SingletonTest {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        singleton.sayHi();
    }
}
