package org.example.jucdemo2.atomic;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

    final AtomicInteger startPosition = new AtomicInteger(0);
    final AtomicInteger wrotePosition = new AtomicInteger(0);
    final AtomicInteger committedPosition = new AtomicInteger(0);
    final AtomicInteger flushedPosition = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        List<AtomicIntegerTest> list = new LinkedList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(new AtomicIntegerTest());
        }
        System.out.println("create instances 1000000");
        System.in.read();
    }
}
