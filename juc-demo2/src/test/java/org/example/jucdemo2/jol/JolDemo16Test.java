 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.concurrent.TimeUnit;

/*
 * This is the example of biased locking conflicting with identity hash
 * code. Identity hash code takes precedence.
 *
 * In order to demonstrate this, we first need to sleep for >5 seconds
 * to pass the grace period of biased locking. Then, we do the same
 * trick as the example before. You may notice that the mark word
 * had not changed after the first lock was released, retaining the bias.
 *
 * The identity hash code computation overwrites the biased locking information,
 * and subsequent locks only displace it temporarily. After the second lock
 * is released, identity hash code data gets back. No biased locking is
 * possible for that object anymore.
 *
 * On JDK 15+, this test should enable -XX:+UseBiasedLocking.
 */
import static java.lang.System.out;


 public class JolDemo16Test {
     public static void main(String[] args) throws Exception {
         final A a = new A();
         TimeUnit.SECONDS.sleep(6);
         ClassLayout layout = ClassLayout.parseInstance(a);
         out.println("**** Fresh object");
         out.println(layout.toPrintable());
         synchronized (a) {
             out.println("**** With the lock");
             out.println(layout.toPrintable());
         }
         out.println("**** After the lock");
         out.println(layout.toPrintable());
         int hashCode = a.hashCode();
         out.println("hashCode: " + Integer.toHexString(hashCode));
         out.println();
         out.println("**** After the hashcode");
         out.println(layout.toPrintable());

         synchronized (a) {
             out.println("**** With the second lock");
             out.println(layout.toPrintable());
         }

         out.println("**** After the second lock");
         out.println(layout.toPrintable());
     }
     public static class A {}
}
