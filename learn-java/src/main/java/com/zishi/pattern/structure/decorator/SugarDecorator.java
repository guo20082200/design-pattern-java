package com.zishi.pattern.structure.decorator;

public class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(ICoffee coffee) {
        super(coffee);
    }
    @Override
    public void makeCoffee() {
        super.makeCoffee();
        addSugar();
    }
    private void addSugar(){
           System.out.print("加糖");
     } 
}