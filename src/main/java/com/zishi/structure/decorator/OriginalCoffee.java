package com.zishi.structure.decorator;

public class OriginalCoffee implements ICoffee {
    @Override
    public void makeCoffee() {
        System.out.print("原味咖啡 ");
    }
}