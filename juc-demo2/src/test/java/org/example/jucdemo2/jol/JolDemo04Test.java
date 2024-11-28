package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;
import static java.lang.System.out;


public class JolDemo04Test {

    public static void main(String[] args) {
        out.println(ClassLayout.parseClass(A.class).toPrintable());
        out.println();
        out.println(ClassLayout.parseClass(B.class).toPrintable());
        out.println();
        out.println(ClassLayout.parseClass(C.class).toPrintable());
    }

    public static class A {
        int a;
        private int d;
        static int r;
        public void test(){}
    }

    public static class B extends A {
        int b;
        protected int e;
    }

    public static class C extends B {
        int c;
    }
}
