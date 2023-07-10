package com.zishi.creational.factory.im01;

public class MPhone implements Phone{
    @Override
    public void call() {
        System.out.println("用小米手机打电话！");
    }
}
