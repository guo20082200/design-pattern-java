package com.zishi.structure.decorator;

public abstract class CoffeeDecorator implements ICoffee {
    private  ICoffee coffee;
    public CoffeeDecorator(ICoffee coffee){
        this.coffee=coffee;
    }

    @Override
    public void makeCoffee() {
        coffee.makeCoffee();
    }
}