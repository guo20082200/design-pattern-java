package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;

import static java.lang.System.out;

public class JolDemo06Test {

    public static void main(String[] args) {
        //out.println(VM.current().details());
        out.println(ClassLayout.parseClass(C.class).toPrintable());
    }

    public static class A {
        long a;
    }

    public static class B extends A {
        long b;
    }

    public static class C extends B {
        long c;
        int d;
    }
}
