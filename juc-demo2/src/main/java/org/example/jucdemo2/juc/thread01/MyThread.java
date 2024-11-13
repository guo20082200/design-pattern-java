package org.example.jucdemo2.juc.thread01;

/**
 * @author zishi
 */
public class MyThread extends Thread {
    @Override
    public void run() {
        int n = 0;

        // 检测当前线程是否被打断，
        // 当前方法不会影响线程的interrupted状态
        while (!isInterrupted()) {
            n++;
            System.out.println(n + " hello!");
        }
    }
}