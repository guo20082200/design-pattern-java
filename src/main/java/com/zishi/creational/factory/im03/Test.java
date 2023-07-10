package com.zishi.creational.factory.im03;

public class Test {
    public static void main(String[] args) {
        // 实例化苹果工厂，生产苹果手机和电脑
        Factory factory = new AppleFactory();
        factory.createBook().play();
        factory.createPhone().call();

        // 实例化小米工厂，生产小米手机和电脑
        Factory factory1 = new XiaoMiFactory();
        factory1.createBook().play();
        factory1.createPhone().call();
    }
}
