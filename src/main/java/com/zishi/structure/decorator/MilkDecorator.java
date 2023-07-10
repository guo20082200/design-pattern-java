package com.zishi.structure.decorator;

public class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(ICoffee coffee) {
        super(coffee);
    }
    @Override
    public void makeCoffee() {
        super.makeCoffee();
        addMilk();
    }
    private void addMilk(){
           System.out.print("加奶 ");
     }    
}