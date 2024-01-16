package com.zishi.pattern.structure.bridge;

//加糖
public class Sugar implements ICoffeeAdditives {
    @Override
    public void addSomething() {
        System.out.println("加糖");
    }
}