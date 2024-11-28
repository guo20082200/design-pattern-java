package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;

import static java.lang.System.out;


public class JolDemo07Test {
    public static void main(String[] args) {
        out.println(ClassLayout.parseClass(Throwable.class).toPrintable());
    }
}
