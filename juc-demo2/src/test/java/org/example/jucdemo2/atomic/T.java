package org.example.jucdemo2.atomic;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

class FieldUpdaterTest {

    public volatile int a = 0;
    private static final AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(FieldUpdaterTest.class, "a");
}

class AtomicTest22 {

    public volatile int a = 0;
    private AtomicInteger atomicInteger = new AtomicInteger(0);
}

public class T {
     public static void main(String[] args) {
        FieldUpdaterTest test = new FieldUpdaterTest();
        System.out.println(ClassLayout.parseInstance(test).toPrintable());

         AtomicTest22 test2 = new AtomicTest22();
        System.out.println(ClassLayout.parseInstance(test2).toPrintable());
    }
}
