package org.example.jucdemo2.juc.thread01;

/**
 * @author zishi
 */
public class ThreadDemo03 {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new MyThread02();
        t.start();
        Thread.sleep(10);
        t.interrupt(); // 中断t线程
        t.join(); // 等待t线程结束
        System.out.println("end");
    }
}



