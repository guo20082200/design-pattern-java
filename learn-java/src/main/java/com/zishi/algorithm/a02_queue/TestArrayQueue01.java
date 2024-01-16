package com.zishi.algorithm.a02_queue;

import java.util.Scanner;

public class TestArrayQueue01 {
    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(3);
        Scanner scanner = new Scanner(System.in);
        char key = ' ';//接收用户输入
        boolean f = true;
        while (f) {
            System.out.println("s(show)：显示队列");
            System.out.println("e(exit)：退出队列");
            System.out.println("a(add)：添加数据到队列");
            System.out.println("g(get)：从队列中获取数据");
            System.out.println("h(head)：显示队列头部数据");
            key = scanner.next().charAt(0);
            switch (key) {
                case 's'://显示队列
                    arrayQueue.showQueue();
                    break;
                case 'a'://添加数据到队列
                    System.out.println("请输入一个数：");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g'://从队列中获取数据
                    try {
                        int result = arrayQueue.getQueue();
                        System.out.printf("从队列中取出的数据是%d\n", result);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h'://显示队列头部数据
                    try {
                        int result = arrayQueue.headQueue();
                        System.out.printf("显示队列的头部数据是%d\n", result);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e'://退出队列
                    scanner.close();
                    f = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出");
    }
}
