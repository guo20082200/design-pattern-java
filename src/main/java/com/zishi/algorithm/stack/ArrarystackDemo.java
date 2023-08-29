package com.zishi.algorithm.stack;

import java.util.Scanner;

public class ArrarystackDemo {
    public static void main(String[] args) {

        //创建对象
        ArrayStack arrarystack = new ArrayStack(5);
        String key = "";
        boolean lool = true;
        Scanner scanner = new Scanner(System.in);
        while (lool) {
            System.out.println("1:show");
            System.out.println("2:push");
            System.out.println("3:pop");
            System.out.println("4:exit");
            key = scanner.next();
            switch (key) {
                case "1":
                    arrarystack.list();
                    break;
                case "2":
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    arrarystack.push(value);
                    break;
                case "3":
                    try {
                        int res = arrarystack.pop();
                        System.out.println(res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":
                    scanner.close();
                    lool = false;
                    break;


            }
        }


    }
}