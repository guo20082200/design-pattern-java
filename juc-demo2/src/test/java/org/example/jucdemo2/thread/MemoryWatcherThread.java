package org.example.jucdemo2.thread;

public class MemoryWatcherThread implements Runnable {
    public static void start () {
        Thread thread = new Thread(new MemoryWatcherThread());
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run () {

        long memoryUsed = getMemoryUsed();
        System.out.println("Memory used :" + memoryUsed + " MB");

        while (true) {
            long memoryUsed1 = getMemoryUsed();
            if (memoryUsed != memoryUsed1) {
                memoryUsed = memoryUsed1;
                System.out.println("Memory used :" + memoryUsed + " MB");
            }
        }
    }

    private long getMemoryUsed () {
        return (Runtime.getRuntime()
                       .totalMemory() - Runtime.getRuntime()
                                               .freeMemory()) / 1024 / 1024;
    }
    
}