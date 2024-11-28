 package org.example.jucdemo2.jol;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.System.out;


 public class JolDemo20Test {

     public static void main(String[] args) {
         PrintWriter pw = new PrintWriter(out, true);

         long last = VM.current().addressOf(new Object());
         for (int l = 0; l < 1000 * 1000 * 1000; l++) {
             long current = VM.current().addressOf(new Object());

             long distance = Math.abs(current - last);
             if (distance > 4096) {
                 pw.printf("Jumping from %x to %x (distance = %d bytes, %dK, %dM)%n",
                         last,
                         current,
                         distance,
                         distance / 1024,
                         distance / 1024 / 1024);
             }

             last = current;
         }

         pw.close();
     }
}
