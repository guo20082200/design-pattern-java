package com.zishi.pattern.structure.decorator;

/**
 * 可以从客户端调用代码看出，装饰者模式的精髓在于动态的给对象增减功能。
 *
 * 当你你需要原味咖啡时那就生成原味咖啡的对象，
 * 而当你需要加奶咖啡时，仅仅需要将原味咖啡对象传递到加奶装饰者中去装饰一下就好了。
 * 如果你加了奶还想加糖，那就把加了奶的咖啡对象丢到加糖装饰者类中去装饰一下，
 * 一个先加奶后加糖的咖啡对象就出来了。
 *
 */
public class TestDemo {

    public static void main(String[] args) {
        //原味咖啡
        ICoffee coffee = new OriginalCoffee();
        coffee.makeCoffee();
        System.out.println("---------------");

        //加奶的咖啡
        coffee = new MilkDecorator(coffee);
        coffee.makeCoffee();
        System.out.println("---------------");

        //先加奶后加糖的咖啡
        coffee = new SugarDecorator(coffee);
        coffee.makeCoffee();
    }
}
