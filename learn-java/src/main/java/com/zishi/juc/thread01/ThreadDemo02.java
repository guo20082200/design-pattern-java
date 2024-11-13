package com.zishi.juc.thread01;

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
public class ThreadDemo02 {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new MyThread();
        t.start();
        Thread.sleep(5); // 暂停1毫秒

        /**
         * 中断线程
         * 1. 除非当前的线程正在被线程自己打断（总是被允许），这里会调用checkAccess() 方法，可能会抛出SecurityException异常
         * 2. 如果线程处在阻塞状态（通过调用wait() 方法，或者是join()防范，或者是sleep方法），那么interrupt状态将会被清除，抛出InterruptedException
         * 3. 如果线程阻塞是因为IO操作（InterruptibleChannel），那么通道将会被关闭，线程的interrupt状态将会被设置，当前线程收到一个ClosedByInterruptException异常
         * 4. 如果线程阻塞在  java.nio.channels.Selector，那么线程的interrupt状态将会被设置，并且会立即从selection operation返回一个可能非0的值，就像调用了Selector的wakeup方法
         * 5. 如果不都不是前几个状态，那么线程的interrupt状态将会被设置
         * 6. 中断一个不是活着的线程没有任何影响
         */
        t.interrupt(); // 中断t线程
        t.join(); // 等待t线程结束
        System.out.println("end");
    }
}



