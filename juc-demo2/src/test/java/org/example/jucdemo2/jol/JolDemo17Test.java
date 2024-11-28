 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;


 public class JolDemo17Test {
     public static void main(String[] args) throws Exception {
         for (int c = 0; c < 8; c++) {
             out.println("**** int[" + c + "]");
             out.println(ClassLayout.parseInstance(new int[c]).toPrintable());
         }
     }
}
