package org.example.jucdemo2.sync;

public class ThreadSynchronizedDemo {
    private Integer counter = 0;

    public static void main (String[] args) throws InterruptedException {
        ThreadSynchronizedDemo demo = new ThreadSynchronizedDemo();
        Task task1 = demo.new Task();
        Thread thread1 = new Thread(task1);

        Task task2 = demo.new Task();
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();
    }

    private synchronized void performTask () {
        int temp = counter;
        counter++;
        System.out.println(Thread.currentThread()
                                 .getName() + " - before: "+temp+" after:" + counter);
    }

    private class Task implements Runnable {

        @Override
        public void run () {
            for (int i = 0; i < 5; i++) {
                performTask();
            }
        }
    }
}