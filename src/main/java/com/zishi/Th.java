package com.zishi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class Th {

    public static void main(String[] args) throws InterruptedException {

        /*new Thread(() -> {
            List<OOMObject> list = new ArrayList<>();
            while (true) {
                System.out.println("....................");
                list.add(new OOMObject());
            }
        }).start();

        while (true) {
            System.out.println(Thread.currentThread().getName() + " continuing...");
            Thread.sleep(1000L);
        }*/

        /*TreeSet<Integer> set = new TreeSet<>();
        Iterator<Integer> iterator = set.iterator();
        System.out.println(iterator.getClass());*/ // TreeMap$KeyIterator

        String s = "xxxxxTreeMap$KeyIterator";
        int i = s.lastIndexOf("Map");
        System.out.println(i);

        System.out.println(s.substring(i + "Map".length()));

        System.out.println(s.substring("xxxxx".length(), i));

    }
}

class OOMObject {
}
