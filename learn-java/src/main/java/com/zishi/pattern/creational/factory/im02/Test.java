package com.zishi.pattern.creational.factory.im02;

public class Test {
    public static void main(String[] args) {
        // 生产小米手机
        PhoneFactory factory1 = new MPhoneFactory();
        factory1.create().call();

        // 生产苹果手机
        PhoneFactory factory2 = new IPhoneFactory();
        factory2.create().call();
    }
}
