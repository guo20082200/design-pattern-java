package com.zishi.pattern.structure.bridge;

import java.util.Random;

//抽象化的修正类
public abstract class RefinedCoffee extends Coffee {
    public RefinedCoffee(ICoffeeAdditives additives) {
        super(additives);
    }

    public void checkQuality() {
        Random ran = new Random();
        System.out.println(String.format("%s 添加%s", additives.getClass().getSimpleName(), ran.nextBoolean() ? "太多" : "正常"));
    }
}