package org.example.jucdemo2.jol;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;


public class JolDemo02Test {
    public static class A {
        long f2;
        //boolean f;

    }
    public static void main(String[] args) {
        System.out.println(ClassLayout.parseClass(A.class).toPrintable());
    }
}
