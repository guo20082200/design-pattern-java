package org.example.jucdemo2.thread;


import java.util.concurrent.TimeUnit;

public class ThreadYieldExample {
    public static void main(String[] args) {
        Task task1 = new Task(true);
        new Thread(task1).start();

        Task task2 = new Task(false);
        new Thread(task2).start();

    }

    private static class Task implements Runnable {
        private final boolean shouldYield;
        private int c;

        public Task(boolean shouldYield) {
            this.shouldYield = shouldYield;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();

            System.out.println(threadName + " started.");
            for (int i = 0; i < 1000; i++) {
                c++;
                if (shouldYield) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Thread.yield();
                }
            }

            System.out.println(threadName + " ended.");
        }
    }
}