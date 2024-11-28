 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;


 public class JolDemo18Test {

     public static void main(String[] args) throws Exception {
         out.println(ClassLayout.parseInstance(new long[1]).toPrintable());
         for (int size = 0; size <= 8; size++) {
             out.println(ClassLayout.parseInstance(new byte[size]).toPrintable());
         }
     }
}
