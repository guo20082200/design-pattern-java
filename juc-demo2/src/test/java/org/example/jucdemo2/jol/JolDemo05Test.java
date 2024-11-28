package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import static java.lang.System.out;

/*
 * This example shows the HotSpot field layout quirk.
 * (Works best with 64-bit VMs)
 *
 * Prior to JDK 15, even though we have the alignment gap before
 * A.a field, HotSpot does not claim it, because it does not track
 * the gaps in the already laid out superclasses.
 *
 * In JDK 15 and later, the superclass gaps are no longer present.
 *
 * See also:
 *    https://bugs.openjdk.java.net/browse/JDK-8237767
 */
public class JolDemo05Test {

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
