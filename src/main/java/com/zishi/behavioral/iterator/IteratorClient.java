package com.zishi.behavioral.iterator;

import java.util.Iterator;

public class IteratorClient {

    public void checkAttendance() {
        MyItr cls = new MyItr();
        System.out.println("--------------开始点名--------------");
        Iterator<Student> iterator = cls.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}