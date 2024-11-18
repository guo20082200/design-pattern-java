package org.example.jucdemo2.thread;


public class Example4 {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            //acquiring lock on Example4.class object
            synchronized (Example4.class) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        MyTask4 t = new MyTask4();
        t.setName("Thread4");
        Example1.runExampleThread(t);
    }

    private static class MyTask4 extends Thread {
        @Override
        public void run() {
            Example1.printState("attempting to enter synchronized block", this);
            synchronized (Example4.class) {
                Example1.printState("entered synchronized block", this);
                //do something
            }
            Example1.printState("thread finishing", this);
        }
    }
}