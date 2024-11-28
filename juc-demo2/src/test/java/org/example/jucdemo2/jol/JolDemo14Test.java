 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.samples.JOLSample_14_FatLocking;
import org.openjdk.jol.vm.VM;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;


 public class JolDemo14Test {
     public static void main(String[] args) throws Exception {

         final A a = new A();

         ClassLayout layout = ClassLayout.parseInstance(a);

         out.println("**** Fresh object");
         out.println(layout.toPrintable());

         Thread t = new Thread(() -> {
             synchronized (a) {
                 try {
                     TimeUnit.SECONDS.sleep(10);
                 } catch (InterruptedException e) {
                     // Do nothing
                 }
             }
         });

         t.start();

         TimeUnit.SECONDS.sleep(1);

         out.println("**** Before the lock");
         out.println(layout.toPrintable());

         synchronized (a) {
             out.println("**** With the lock");
             out.println(layout.toPrintable());
         }

         out.println("**** After the lock");
         out.println(layout.toPrintable());

         System.gc();

         out.println("**** After System.gc()");
         out.println(layout.toPrintable());
     }

     public static class A {}
}
