 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.samples.JOLSample_15_IdentityHashCode;
import org.openjdk.jol.vm.VM;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

 /*
  * The example for identity hash code.
  *
  * The identity hash code, once computed, should stay the same.
  * HotSpot opts to store the hash code in the mark word as well.
  * You can clearly see the hash code bytes in the header once
  * it was computed.
  */
 public class JolDemo15Test {

     public static void main(String[] args) {

         final A a = new A();

         ClassLayout layout = ClassLayout.parseInstance(a);

         out.println("**** Fresh object");
         out.println(layout.toPrintable());

         out.println("-------------->hashCode: " + Integer.toHexString(a.hashCode()));
         out.println();

         out.println("**** After identityHashCode()");
         out.println(layout.toPrintable());
     }

     public static class A {}
}
