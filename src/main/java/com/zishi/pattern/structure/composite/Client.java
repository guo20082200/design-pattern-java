package com.zishi.pattern.structure.composite;

public class Client {

    public static void main(String[] args) {
        MenuComponent component = new Menu("crush", 2);
        MenuComponent c2 = new Menu("wyh1", 1);
        MenuComponent wyh4 = new MenuItem("wyh4", 2);
        MenuComponent wyh5 = new MenuItem("wyh5", 2);

        component.add(c2);

        component.add(wyh4);
        //wyh4.add(wyh5);

        component.print();
        /**
         * 输出：
         * --crush
         * wyh1
         * --wyh4
         */
    }
}
