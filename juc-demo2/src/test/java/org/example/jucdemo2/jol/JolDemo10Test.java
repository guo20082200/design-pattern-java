package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;

import static java.lang.System.out;


public class JolDemo10Test {
    public static void main(String[] args) {
        out.println(ClassLayout.parseClass(Class.class).toPrintable());
    }
}
