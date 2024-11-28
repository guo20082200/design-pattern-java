package org.example.jucdemo2.jol;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;


public class JolDemo01Test {

    /**
     * # VM mode: 64 bits
     * # Compressed references (oops): 3-bit shift
     * # Compressed class pointers: 0-bit shift and 0x26510000000 base
     * # Object alignment: 8 bytes
     * #                       ref, bool, byte, char, shrt,  int,  flt,  lng,  dbl
     * # Field sizes:            4,    1,    1,    2,    2,    4,    4,    8,    8
     * # Array element sizes:    4,    1,    1,    2,    2,    4,    4,    8,    8
     * # Array base offsets:    16,   16,   16,   16,   16,   16,   16,   16,   16
     */
    @Test
    void testDetails() {
        String details = VM.current().details();
        System.out.println(details);
    }


    /*
     * This sample showcases the basic field layout.
     * You can see a few notable things here:
     *   a) how much the object header consumes;
     *   b) how fields are laid out;
     *   c) how the external alignment beefs up the object size
     */


    public static class A {
        boolean f;
    }

    public static void main(String[] args) {
        System.out.println(ClassLayout.parseClass(A.class).toPrintable());
    }

}
