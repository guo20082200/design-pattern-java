package org.example.jucdemo2.juc.thread01;

public class HelloThread extends Thread {
    @Override
    public void run() {
        int n = 0;
        while (!isInterrupted()) {
            n++;
            System.out.println(n + " hello!");
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}