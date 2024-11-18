package org.example.jucdemo2.thread;

import java.util.ArrayList;
import java.util.List;

public class MainApp {
    static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        MemoryWatcherThread.start();
        for (int i = 0; i < 100000; i++) {
            String str = new String("str" + i);
            list.add(str);
        }

        System.out.println("end of main method");
        System.exit(0);
    }
}