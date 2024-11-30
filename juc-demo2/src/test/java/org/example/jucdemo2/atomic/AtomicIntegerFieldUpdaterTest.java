package org.example.jucdemo2.atomic;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterTest {

    public static final AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterTest> startPosition =  AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterTest.class,"startPositionInt");
    public static final AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterTest> wrotePosition = AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterTest.class,"wrotePositionInt");
    public static final AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterTest> committedPosition = AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterTest.class,"committedPositionInt");
    public static final AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterTest> flushedPosition =AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterTest.class,"flushedPositionInt");

    private volatile int startPositionInt = 0;
    private volatile int wrotePositionInt = 0;
    private volatile int committedPositionInt = 0;
    private volatile int flushedPositionInt = 0;

    public static void main(String[] args) throws Exception{
        List<AtomicIntegerFieldUpdaterTest> list = new LinkedList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(new AtomicIntegerFieldUpdaterTest());
        }
        System.out.println("create instances 1000000");
        System.in.read();
    }
}
