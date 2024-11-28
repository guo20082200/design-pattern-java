package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.samples.JOLSample_11_ClassWord;
import org.openjdk.jol.vm.VM;

import static java.lang.System.out;


public class JolDemo11Test {
    public static void main(String[] args) {
        out.println(ClassLayout.parseInstance(new A()).toPrintable());
        out.println(ClassLayout.parseInstance(new B()).toPrintable());
    }

    public static class A {
    }

    public static class B {
    }
}
