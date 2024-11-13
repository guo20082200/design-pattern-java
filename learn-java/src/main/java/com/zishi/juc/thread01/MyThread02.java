package com.zishi.juc.thread01;

/**
 * @author zishi
 */
public class MyThread02 extends Thread {
    @Override
    public void run() {
        Thread hello = new HelloThread();
        hello.start(); // 启动hello线程
        try {
            hello.join(); // 等待hello线程结束
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("......................interrupted!");
        }
        hello.interrupt();
    }
}