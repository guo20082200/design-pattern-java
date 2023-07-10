package com.zishi.structure.bridge;

//小杯
public class SmallCoffee extends RefinedCoffee {
    public SmallCoffee(ICoffeeAdditives additives) {
        super(additives);
    }

    @Override
    public void orderCoffee(int count) {
        additives.addSomething();
        System.out.println(String.format("小杯咖啡%d杯", count));
    }
}