package org.example.jucdemo2.jol;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.samples.JOLSample_23_Roots;
import org.openjdk.jol.vm.VM;

import java.io.PrintWriter;
import java.util.Random;

import static java.lang.System.out;


 public class JolDemo23Test {

     static volatile Object sink;

     public interface L {
         L link();
         void bind(L l);
     }

     public static abstract class AL implements L {
         L l;
         public L link() { return l; }
         public void bind(L l) { this.l = l; }
     }

     public static class L1 extends AL {}
     public static class L2 extends AL {}
     public static class L3 extends AL {}
     public static class L4 extends AL {}
     public static class L5 extends AL {}
     public static class L6 extends AL {}

     public static void main(String[] args) {
         out.println(VM.current().details());

         PrintWriter pw = new PrintWriter(System.out, true);

         // create links
         L l1 = new L1();
         L l2 = new L2();
         L l3 = new L3();
         L l4 = new L4();
         L l5 = new L5();
         L l6 = new L6();

         // bind the ring
         l1.bind(l2);
         l2.bind(l3);
         l3.bind(l4);
         l4.bind(l5);
         l5.bind(l6);
         l6.bind(l1);

         // current root
         L r = l1;

         // break all other roots
         l1 = l2 = l3 = l4 = l5 = l6 = null;

         long lastAddr = VM.current().addressOf(r);
         pw.printf("Fresh object is at %x%n", lastAddr);

         int moves = 0;
         for (int i = 0; i < 100000; i++) {

             // scan for L1 and determine it's address
             L s = r;
             while (!((s = s.link()) instanceof L1)) ;

             long cur = VM.current().addressOf(s);
             s = null;

             // if L1 had moved, then probably the entire ring had also moved
             if (cur != lastAddr) {
                 moves++;
                 pw.printf("*** Move %2d, L1 is at %x%n", moves, cur);
                 pw.println("*** Root is " + r.getClass());

                 pw.println(GraphLayout.parseInstance(r).toPrintable());

                 // select another link
                 Random random = new Random();
                 for (int c = 0; c < random.nextInt(100); c++) {
                     r = r.link();
                 }

                 lastAddr = cur;
             }

             // make garbage
             for (int c = 0; c < 10000; c++) {
                 sink = new Object();
             }
         }

         pw.close();
     }
}
