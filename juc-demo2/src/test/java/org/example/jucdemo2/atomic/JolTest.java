package org.example.jucdemo2.atomic;

import org.example.jucdemo2.jol.JolDemo01Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.layouters.HotSpotLayouter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 单个对象的大小
 */
public class JolTest {

    public static void main(String[] args) {
        AtomicIntegerTest atomicIntegerTest = new AtomicIntegerTest();

        System.out.println(ClassLayout.parseInstance(atomicIntegerTest).toPrintable());
        System.out.println();

        AtomicIntegerFieldUpdaterTest atomicIntegerFieldUpdaterTest = new AtomicIntegerFieldUpdaterTest();
        System.out.println(ClassLayout.parseInstance(atomicIntegerFieldUpdaterTest).toPrintable());
    }
}
