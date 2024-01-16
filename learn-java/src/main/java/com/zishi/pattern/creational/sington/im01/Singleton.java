package com.zishi.pattern.creational.sington.im01;

/**
 * 饿汉模式的实现
 *
 * 它的优点是线程安全，因为单例对象在类加载的时候就已经被初始化了，当调用单例对象时只是把早已经创建好的对象赋值给变量；
 * 它的缺点是可能会造成资源浪费，如果类加载了单例对象（对象被创建了），但是一直没有使用，这样就造成了资源的浪费。
 */
public class Singleton {
    // 声明私有对象
    private static final Singleton instance = new Singleton();

    private Singleton() {
    }

    // 获取实例（单例对象）
    public static Singleton getInstance() {
        return instance;
    }

    // 方法
    public void sayHi() {
        System.out.println("Hi,Java.");
    }
}

class SingletonTest {
    public static void main(String[] args) {
        // 调用单例对象
        Singleton singleton = Singleton.getInstance();
        // 调用方法
        singleton.sayHi();
    }
}
