package com.zishi.pattern.structure.bridge;

//小杯
public class MidCoffee extends RefinedCoffee {
    public MidCoffee(ICoffeeAdditives additives) {
        super(additives);
    }

    @Override
    public void orderCoffee(int count) {
        additives.addSomething();
        System.out.println(String.format("中杯咖啡%d杯", count));
    }
}