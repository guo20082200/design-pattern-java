package com.zishi.jvm;

import java.util.ArrayList;
import java.util.List;

public class Th {

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            List<OOMObject> list = new ArrayList<>();
            while (true) {
                System.out.println("....................");
                list.add(new OOMObject());
            }
        }).start();

        while (true) {
            System.out.println(Thread.currentThread().getName() + " continuing...");
            Thread.sleep(1000L);
        }

    }
}


