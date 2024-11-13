package org.example.jucdemo2.juc.thread01;

/**
 * 线程的状态
 * New：新创建的线程，尚未执行；
 * Runnable：运行中的线程，正在执行run()方法的Java代码；
 * Blocked：运行中的线程，因为某些操作被阻塞而挂起；
 * Waiting：运行中的线程，因为某些操作在等待中；
 * Timed Waiting：运行中的线程，因为执行sleep()方法正在计时等待；
 * Terminated：线程已终止，因为run()方法执行完毕。
 *
 * @author zishi
 */
public class ThreadDemo01 {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            System.out.println("hello");

            while (true) {

            }
        });
        System.out.println("start");
        t.start(); // 启动t线程

        /**
         * 等待当前线程死亡
         */
        t.join(); // 此处main线程会等待t结束
        System.out.println("end");
    }
}


