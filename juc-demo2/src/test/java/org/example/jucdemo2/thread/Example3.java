package org.example.jucdemo2.thread;


public class Example3 {

    public static void main(String[] args) throws InterruptedException {
        MyTask3 t = new MyTask3();
        t.setName("Thread3");
        Example1.runExampleThread(t);
        synchronized (Example3.class) {
            Example3.class.notifyAll();
        }
    }

    private static class MyTask3 extends Thread {

        @Override
        public void run() {
            Example1.printState("thread run() started", this);
            //using Example3.class object monitor
            synchronized (Example3.class) {
                try {
                    //this will put the thread in WAITING state
                    Example3.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Example1.printState("thread finishing", this);
        }
    }
}